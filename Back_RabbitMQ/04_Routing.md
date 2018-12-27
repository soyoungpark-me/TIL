# 4. Routing

![RabbitMQ](https://blogfiles.pstatic.net/MjAxODA5MTFfMjE4/MDAxNTM2NjQ2NDE4NDY4.NZZl0OeDAjlIZxVKVinYvGHMWBEQd8ldAuBh14Ieeo0g.QEVZExemlx_CIfIH36pOZtGydl5GzxFEPoO3Mb11Mscg.PNG.3457soso/1280px-RabbitMQ_logo.png)

작성일 : ```2018.09.11```

**RabbitMQ의 Routing 기능 활용하기**



___

### Routing을 쓰는 이유와 방법

- 이전에는 모든 ```receiver```들에게 메시지를 **broadcast** 했다.

- 이번에는 모든 메시지를 받아보는 게 아니라, 일부만 선택적으로 **```subscribe```** 한다.

- ```binding```에게 키를 줘야 한다.

  ```ch.bindQueue(큐 이름, ex이름, key 내용);```

  ```excahnge```의 타입을 ```fanout```으로 할 경우 **key의 내용은 무시된다.**

  

___

### Routing 설정하기

#### Direct Exchange

- 모든 메시지를 다 받지 말고 선택적으로 받자!

  Ex) 로그 중에서 "error" 로그 메시지만 받자.

- 이렇게 하려면 ```fanout```이 아닌 ```direct``` 타입을 써야 한다.

  - **```direct``` 타입의 알고리즘**

    메시지는 메시지의 ```routing queue```와 일치하는 ```binding key```를 가진 큐로 간다!

  ![이미지](https://blogfiles.pstatic.net/MjAxODA5MTFfNTQg/MDAxNTM2NjUxMzQyNzM5.EmNwSzAgaQ3T6b86TPS-XkXdPGvQOeUAQ3mc-N6gwgQg.h2XXI9p9WyL2RJJ7bg4zqiJYjuDKQFeSppnBkoC2g9Ig.JPEG.3457soso/direct-exchange.jpg)

- ```direct exchange [X]```가 2개의 큐에 연결된다.

- 첫번째 큐는 ```orange```에, 두번째 큐는 ```black```과 ```green```에 연결된다.

- ```orange``` 키를 가지고 ```exchange```에 들어가는 메시지는 **Q1**으로 라우팅된다.



#### Multiple Bindings

- 같은 ```binding key```로 ```multiple queue```를 바인딩해도 된다!

  ![이미지](https://blogfiles.pstatic.net/MjAxODA5MTFfMTQy/MDAxNTM2NjUxMzQzMjc5.nNDk2YvCwt--YWOERIqykF-eLmAGYJWnNRdHqTcvv4og.XGGQcS13o5DkvsIAJHtdLMO0VJe0ibjSYIhHum1CJ5og.JPEG.3457soso/direct-exchange-multiple.jpg)

  - 위처럼 설정하면, ```direct exchange```는 ```fanout exchange```처럼 행동한다.

    > 매칭되는 모든 큐에게 **broadcasting..**
    >
    > 예제에선 Q1, Q2에게!

    

____

#### emit_logs_direct.js

```javascript
#!/usr/bin/env node

var amqp = require('amqplib/callback_api');

amqp.connect('amqp://localhost', function(err, conn) {
      // 먼저 rabbitMQ 서버에 연결하고,
  conn.createChannel(function(err, ch) {
      // 채널을 생성하고 시작한다!

    var ex = 'direct_logs';
      // 늘 그렇듯, exchange부터 먼저 생성한다.
      
    var args = process.argv.slice(2);
    var msg = args.slice(1).join(' ') || 'Hello World!';
      // 전송할 메시지의 내용이 된다.

    var severity = (args.length > 0) ? args[0] : 'info';
      // args에 따라서 바인딩 키를 생성한다.

    ch.assertExchange(ex, 'direct', {durable: false});
      // fanout 타입이 아니라 direct 타입으로 exchange를 생성한다.

    ch.publish(ex, severity, new Buffer(msg));
      // 메시지를 publish 할 때, 
      // log 심각도(? severity)를 routing key로 사용하게 된다.
      // severity : info, warning, error 중 하나.

    console.log(" [x] Sent %s: '%s'", severity, msg);
  });

  setTimeout(function() { conn.close(); process.exit(0) }, 500);
});

/*  
    ./emit_log_direct.js error "Run. Run. Or it will explode."
    error 메시지를 emit한다.
*/
```



#### receive_logs_direct.js

```javascript
#!/usr/bin/env node

var amqp = require('amqplib/callback_api');

var args = process.argv.slice(2);

if (args.length == 0) {
  console.log("Usage: receive_logs_direct.js [info] [warning] [error]");
  process.exit(1);
}

amqp.connect('amqp://localhost', function(err, conn) {
  conn.createChannel(function(err, ch) {
    var ex = 'direct_logs';

    ch.assertExchange(ex, 'direct', {durable: false});
      // direct 타입의 exchange에 연결된다.

    ch.assertQueue('', {exclusive: true}, function(err, q) {
      // 큐의 이름이 없다 > 연결된 모든 큐를 대상으로 한다.
      // exclusive가 true이므로 RabbitMQ로부터 랜덤으로 큐의 이름을 받는다.

      console.log(' [*] Waiting for logs. To exit press CTRL+C');

      args.forEach(function(severity) {
        ch.bindQueue(q.queue, ex, severity);
          // 받는 args마다 바인딩해준다. (exchange와 큐의 관계 설정)
          // ch.bindQueue(큐 이름, ex이름, 바인딩 키);
      });

      ch.consume(q.queue, function(msg) {
          // 
        console.log(" [x] %s: '%s'", msg.fields.routingKey, msg.content.toString());
      }, {noAck: true});
    });
  });
});

/*  
    ./receive_logs_direct.js warning error > logs_from_rabbit.log
    이렇게 설정하면'waring'과 'error' 로그 메시지만 받아오게 된다.
    그리고 결과를 logs_from_rabbit.log에 저장한다.

    ./receive_logs_direct.js info warning error
    모든 로그 메시지를 가져온다.
*/
```



___

### 참고 문헌

RabbitMQ 공식 홈페이지의 튜토리얼을 번역하며 공부한 내용입니다.

- [RabbitMQ tutorial : Routing](https://www.rabbitmq.com/tutorials/tutorial-four-javascript.html)