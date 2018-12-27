# Pub/sub

![RabbitMQ](https://blogfiles.pstatic.net/MjAxODA5MTFfMjE4/MDAxNTM2NjQ2NDE4NDY4.NZZl0OeDAjlIZxVKVinYvGHMWBEQd8ldAuBh14Ieeo0g.QEVZExemlx_CIfIH36pOZtGydl5GzxFEPoO3Mb11Mscg.PNG.3457soso/1280px-RabbitMQ_logo.png)

작성일 : ```2018.09.11```

**RabbitMQ의 Pub/sub 기능 활용하기**



___

### Work Queues란?

![이미지](https://blogfiles.pstatic.net/MjAxODA5MTFfMjAy/MDAxNTM2NjUxMzQ2OTQ4.X5SbVd2jBmcPk45TXeEPsR5d5H4SRE4p67mSwPZ8mw8g.2f_wgO-2V7CCdnLEAE6bHNiVO23CU5mbPS9aV3mXSJ4g.JPEG.3457soso/python-two.jpg)

작업 큐를 만들어서 ```time-consuming task```를 여러 ```worker```에게 분할한다.

- ```resource-intentive```한 ```task```를 행하면서 계속 끝나기를 기다리는 **비효율적**인 현상을 방지한다.

- 대신에 ```task```가 나중에 실행되도록 스케줄링 하는 것!

  1. ```task```를 ```message```로 만들어서 큐에 보낸다.

  2. ```worker``` 프로세스는 백그라운드에서 ```task```를 뽑아서 처리한다.

     > ```worker```가 여러 개일 경우, ```task```들은 워커들 사이에 공유된다.



- **장점**

  - 쉽게 병행 처리를 할 수 있게 된다.

  - 쉽게 ```worker```의 scale-out을 만들어낼 수 있다.

    

___

### Work Queues의 특징과 기능들

#### Round-Robin Dispatching

- ```RabbitMQ```는 기본적으로 **라운드로빈 형식**으로 ```consumer```에게 일을 준다.

  따라서 평균적으로 각 ```consumer```들은 같은 수의 메시지들을 가지게 된다.

  

#### Message Acknowledgement

- 이 상황에서 한 ```cousumer```가 엄청 긴 ```task```를 맡아서 하게 되었다면? **심지어 하다가 죽는다면?**

  ... 현재는 ```RabbitMQ```가 메시지를 전달하고 나면 큐에서 해당 메시지를 즉시 삭제하기 때문에, 처리 중에 죽는 메시지는 유실된다.

  ... 특정 ```worker```에게 전달되었지만 아직 처리되지 않은 메시지도 날아간다.

- **Message Acknowledgment** : 이런 유실 현상을 방지하기 위한 방법이다.

  - 특정 메시지를 수신하고 처리까지 마친 뒤에, ```RabbitMQ```에게 해당 메시지를 지워도 된다고 알려준다.

  - ```consumer```가 ```ack```를 보내지 않은 채로 죽는다면, ```RabbitMQ```는 해당 메시지가 완벽히 처리되지 않은 걸로 이해하고 **re-queue**한다.

    그리고 다른 ```consumer```에게 해당 메시지를 넘긴다.

  - **메시지가 오가는 채널과 같은 채널에 ```ack```를 전송해야 함에 유의한다.**

  

#### Message Durability

- ```ack```를 써도, ```RabbitMQ``` 서버 자체가 죽어버리면 현재 메시지들을 또 잃게 될 수도 있다.

- 큐와 메시지를 둘다 **durable** 하게 세팅해 이런 일을 막는다.

  ```ch.assertQueue(큐_이름, { durable: true });```

  ```ch.sendToQueue(큐_이름, 메시지, { persistent: true });```



#### Fair Dispatch

![이미지](https://blogfiles.pstatic.net/MjAxODA5MTFfMjA2/MDAxNTM2NjUxMzQ0MjE2.E8iLRpQVNchlczzSbKxPsU4jibDjWH9utDjzlCgu48og.SyUXffEo_tjJUchWRFVu-ce-bMN8pFabzLnIiC6lOUgg.JPEG.3457soso/prefetch-count.jpg)

- 라운드로빈 방식의 경우, 특정 ```worker```에게 작업이 몰릴 수 있다는 단점이 있다.
- 이를 방지하기 위해 ```prefetch()```를 사용한다.
- ```ch.prefetch(1)``` 
  - 해당 ```worker```에게 한 번에 하나의 일만 주라고 하는 것!
  - 이전에 준 메시지에 대한 ```ack```가 오지 않았으면 새 메시지를 할당하지 말고, 다음 순서에 있는 ```worker```엑 ㅔ넘기도록 한다.
- 근데 이렇게 하다보면 밀린 일들이 처리가 되지 않아 **큐가 꽉 찰 수 있음**에 유의해야 한다.



____

#### new_task.js

```javascript
#!/usr/bin/env node

var amqp = require('amqplib/callback_api');

amqp.connect('amqp://localhost', function(err, conn) {
  conn.createChannel(function(err, ch) {
    var q = 'task_queue';
    var msg = process.argv.slice(2).join(' ') || "Hello World!";
      // 보낼 메시지의 내용을 argv로 받아온다.

    ch.assertQueue(q, {durable: true});
      // durable을 true로 설정해 RabbitMQ 서버가 죽어도 잃어버리지 않게 한다.

    ch.sendToQueue(q, new Buffer(msg), {persistent: true});
      // persistent을 true로 설정해 잃어버리지 않게 한다.
      
    console.log(" [x] Sent '%s'", msg);
  });
  setTimeout(function() { conn.close(); process.exit(0) }, 500);
});
```



#### worker.js

```javascript
#!/usr/bin/env node

var amqp = require('amqplib/callback_api');

amqp.connect('amqp://localhost', function(err, conn) {
  conn.createChannel(function(err, ch) {
    var q = 'task_queue';

    ch.assertQueue(q, {durable: true});
    ch.prefetch(1);
      // prefetch를 1로 설정해 1개 이상의 메시지를 동시에 받지 않도록 한다.

    console.log(" [*] Waiting for messages in %s. To exit press CTRL+C", q);

    ch.consume(q, function(msg) {
      var secs = msg.content.toString().split('.').length - 1;

      console.log(" [x] Received %s", msg.content.toString());
      setTimeout(function() {
        console.log(" [x] Done");
        ch.ack(msg);
      }, secs * 1000);  // setTimeout을 통해 메시지를 처리하는 데 시간이 드는 '척'한다.
    }, {noAck: false}); // noAck을 false로 설정해 RabbitMQ에게 ack를 전달하도록 한다.
  });
});
```



___

### 참고 문헌

RabbitMQ 공식 홈페이지의 튜토리얼을 번역하며 공부한 내용입니다.

- [RabbitMQ tutorial : Work Queues](https://www.rabbitmq.com/tutorials/tutorial-two-javascript.html)