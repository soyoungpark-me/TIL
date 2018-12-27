# 5. Topics

![RabbitMQ](https://blogfiles.pstatic.net/MjAxODA5MTFfMjE4/MDAxNTM2NjQ2NDE4NDY4.NZZl0OeDAjlIZxVKVinYvGHMWBEQd8ldAuBh14Ieeo0g.QEVZExemlx_CIfIH36pOZtGydl5GzxFEPoO3Mb11Mscg.PNG.3457soso/1280px-RabbitMQ_logo.png)

작성일 : ```2018.09.11```

**RabbitMQ의 Topic 기능 활용하기**



___

### Topic을 쓰는 이유

- 이전에는 ```fanout``` 타입 대신, ```direct``` 타입을 이용했다.

  이를 통해 선택적으로 ```exchange```와 큐를 바인딩해줬다.

- 이제 하나의 기준이 아니라, 여러개의 기준에 의한 (multiple criteria) 라우팅을 해주고 싶다.

  - 현재 : 로그의 심각도 (error, warning, info)에 따른 바인딩
  - 개선 : 로그의 심각도와 소스를 짬뽕시킨 바인딩
    - Ex) 심각도 (error, warning, info) + 소스 (cron, kern ...)

  

___

### Topic Exchange

#### Topic과 routing key

- ```exchange```로 보내지는 메시지는 임의의 ```routing key```를 가질 수 없다.

  > ```dot```으로 구별되는 단어의 리스트를 가져야 한다.
  >
  > Ex) "stack.usd.nyse", "nyse.vmw", "quick.orange.rabbit"

- ```routing key```는 255 byte의 길이 제한을 가진다.

- ```binding key```와 동일한 로직으로 돌아간다.

  **특정 라우팅 키로 전송된 메시지는 일치하는 바인딩 키로 바인딩된 모든 대기열에 전달된다**

- ```binding key```의 특수 케이스

  - \* (star) : 정확히 하나의 단어로 대체된다.
  - \# (hash) : 0개 이상의 단어로 대체된다.

  ![이미지](https://blogfiles.pstatic.net/MjAxODA5MTFfMjEy/MDAxNTM2NjUxMzQ1MTMw.rF5WMNLPa7In6S_w9Kxb-mXLgMXgOR2MeW8gDtB3IhYg.XLzUb8-UoDKB06awFICq7APqMSIXLALzJausPR7rQqIg.JPEG.3457soso/python-five.jpg)

  - ```routing key```는 3개의 단어로 구성되어 있다.

    - <speed>.<color>.<species> 를 의미한다. (동물 묘사)

    - **Q1**: 모든 ```orange``` 색의 ```animal```을 모두 받는다.

    - **Q2** : ```rabbit```에 대한 모든 걸 듣거나, ```lazy```한 동물에 대한 모든 걸 받는다.

      ```
      "quick.orange.rabbit" : Q1, Q2
      
      "lazy.orange.elephant" : Q1, Q2
      
      "quick.orange.fox" : Q1
      
      "lazy.brown.fox" : Q2
      
      "quick.brown.fox" : 둘 다 받지 않는다.
      
      "orange" : 둘다 받지 않는다. (글자 수 부족)
      
      "lazy.orange.male.rabbit" : Q2 (글자 수는 안맞지만 #이라 괜찮다)
      ```

      

#### 추가사항

- ```topic exchange```는 다른 ```exchange```들보다 강력하다.
  - \#으로 바인딩 : ```fanout exchange```와 같이 라우팅 키에 관계없이 모든 메시지 수신
  - \*, \#가 바인딩에 사용되지 X : ```direct exchange```와 같이 작동.



____

#### emit_logs_topic.js

```javascript
#!/usr/bin/env node

var amqp = require('amqplib/callback_api');

amqp.connect('amqp://localhost', function(err, conn) {
  conn.createChannel(function(err, ch) {
    var ex = 'topic_logs';
      // exchange를 생성하는 것까지는 동일하다.

    var args = process.argv.slice(2);
    var key = (args.length > 0) ? args[0] : 'anonymous.info';
      // args에 따라서 라우팅 키를 생성한다.

    var msg = args.slice(1).join(' ') || 'Hello World!';
      // 전송할 메시지의 내용이 된다.

    ch.assertExchange(ex, 'topic', {durable: false});
      // direct 타입이 아니라 topic 타입으로 exchange를 생성한다.

    ch.publish(ex, key, new Buffer(msg));
      // 메시지를 publish 할 때, args에 따라 생성된 라우팅 키를 적용한다.

    console.log(" [x] Sent %s: '%s'", key, msg);
  });

  setTimeout(function() { conn.close(); process.exit(0) }, 500);
});

/*
    ./emit_log_topic.js "kern.critical" "A critical kernel error"
    "kern.critical" 타입의 라우팅 키를 가진 메시지를 생성해 전송한다.
*/
```



#### receive_logs_topic.js

```javascript
#!/usr/bin/env node

var amqp = require('amqplib/callback_api');

var args = process.argv.slice(2);

if (args.length == 0) {
  console.log("Usage: receive_logs_topic.js <facility>.<severity>");
  process.exit(1);
}

amqp.connect('amqp://localhost', function(err, conn) {
  conn.createChannel(function(err, ch) {
    var ex = 'topic_logs';

    ch.assertExchange(ex, 'topic', {durable: false});
      // topic 타입의 exchange에 연결된다.

    ch.assertQueue('', {exclusive: true}, function(err, q) {
      console.log(' [*] Waiting for logs. To exit press CTRL+C');

      args.forEach(function(key) {
        ch.bindQueue(q.queue, ex, key);
          // 방법은 비슷한데, 바인딩 키가 아니라 라우팅 키를 생성해
          // 큐에 연결하는 점이 다르다. (severity > key)
      });

      ch.consume(q.queue, function(msg) {
        console.log(" [x] %s:'%s'", msg.fields.routingKey, msg.content.toString());
      }, {noAck: true});
    });
  });
});

/*
    ./receive_logs_topic.js "#"
    모든 로그를 받는다.

    ./receive_logs_topic.js "kern.*"
    kern에서부터 오는 로그를 받는다.

    ./receive_logs_topic.js "*.critical"
    로그의 severity 중 critical 한 것만 받는다.

    ./receive_logs_topic.js "kern.*" "*.critical"
    multiple binding도 설정할 수 있다.
*/  
```



___

### 참고 문헌

RabbitMQ 공식 홈페이지의 튜토리얼을 번역하며 공부한 내용입니다.

- [RabbitMQ tutorial : Topic](https://www.rabbitmq.com/tutorials/tutorial-five-javascript.html)