# RabbitMQ 매뉴얼

#### 목차

1. **RabbitMQ 소개**
2. Work Queue 활용
3. Pub/sub 기능 활용
4. Routing 활용
5. Topic 활용

작성일 : ```2018.09.11```



___

## 1. RabbitMQ 소개

### RabbitMQ의 정의

- **MQ** : ```Message Queue```의 약자로, 메시지 브로커를 의미한다.

  큐에 전달 받은 메시지를 다른 곳으로 전달하는 역할을 한다.

  ```AMQP``` 프로토콜을 기반으로 한다.

- **AMQP** : ```Advanced Message Queuing Protocol```.

  클라이언트 어플리케이션과 미들웨어 브로커가 메시지를 주고 받을 때 쓴다.

- ```RabbitMQ```는 **비동기 처리**를 위한 메시지 큐 브로커로, ```pub/sub```방식을 지원한다.



___

### 동작 방법 이해하기

- **우체부**와 유사하다고 생각하면 된다.

- 보내고 싶은 메일(```message```)를 우체통(```Queue```)에 넣으면 우체부(```Worker```)들이 전해준다. (처리해준다.)

  

![공식 이미지](https://blogfiles.pstatic.net/MjAxODA5MTFfMjg5/MDAxNTM2NjUxMzQ2MDYx.CE1jXfC641IEIruqJ247KWpLUnl1k-79Rg_YSGJkKQYg.BZWC7FvSmJMN56K2ADglLXnAx-Z1ym8otrkdngHBHQ0g.JPEG.3457soso/python-one.jpg)

- **producing** : send 하는 것과 같다. 메시지를 보내는 일을 하는 프로그램이 ```producer```

- **queue** : ```RabbitMQ``` 내에 있는 우체통이다. 메시지는 ```RabbitMQ```와 프로그램 사이를 오가지만, **큐에만 저장된다**

  호스트 컴퓨터의 메모리나 디스크에 존재한다.

- **consuming** : receiving과 비슷하다.메시지를 받는 걸 기다리는 프로그램이 ```consumer```

  > Javascript에서는 ```amqp.node API```를 사용한다.



___

### Sending

![sending](https://blogfiles.pstatic.net/MjAxODA5MTFfNjAg/MDAxNTM2NjUxMzQ3ODc2.HO_uLjxgtaLrm3iSjwbi9-fqLRLB7xsoaOCsgTjUt8Ag.zJt6P4WR7zqVxqGsxwtSYI2VbjwoT7dRG4hz2h8Hrq0g.JPEG.3457soso/sending.jpg)

1. 해당 라이브러리를 import하고,
2. ```RabbitMQ``` 서버에 연결한 뒤에,
3. 대부분의 API가 존재하는 채널을 생성한다 (?)
4. 해당 채널 내에 큐를 정의한다. 이 큐 내에 메시지를 ```publish``` 할 수 있다.
   - 큐는 이미 존재하지 않을 때에만 생성된다.
   - 메시지 내용은 ```bite array```이므로 아무거나 인코딩해서 집어넣을 수 있다.
5. 작업이 끝나면 커넥션을 끊고 나간다.

```javascript
#!/usr/bin/env node

var amqp = require('amqplib/callback_api');

// 1. RabbitMQ 서버에 연결한다.
amqp.connect('amqp://localhost', function(err, conn) {
  // 2. 채널을 생성한다.
  conn.createChannel(function(err, ch) {

    // 3. 큐를 생성한다.
    var q = 'hello';

    // 보낼 메시지의 내용
    var msg = 'Hello World!';

    ch.assertQueue(q, {durable: false});

    // 4. 큐에 해당 메시지를 넣는다.
    ch.sendToQueue(q, Buffer.from(msg));
    console.log(" [x] Sent %s", msg);
  });
  setTimeout(function() { conn.close(); process.exit(0) }, 500);
});
```



### Receiving

![receiving](https://blogfiles.pstatic.net/MjAxODA5MTFfMjMg/MDAxNTM2NjUxMzQ3MzIy.KxEWYG2qlqz62P0XSf64LeiyWy8voahf7VMJjJjWsOAg.GZV_LdAGC1aTN23orbRoy29aOGW-RBoDhPCp0SLIJwMg.JPEG.3457soso/receiving.jpg)

1. ```sending```과는 다르게, 메시지가 생성되는 것을 기다리며 **listen** 상태로 있어야 한다.
2. ```sending``` 과정과 비슷하지만, 큐에서 메시지를 꺼내기 전에 큐가 존재하는 걸 꼭 확인해야 한다.
3. 해당 채널과 큐를 할당해서, ```consume()``` 함수로 받은 메시지를 처리할 수 있도록 한다.
   - 비동기식으로 메시지를 전달하므로, ```RabbitMQ```가 ```consumer```에게 메시지를 전달할 때 실행할 콜백 함수가 필요하다.

```javascript
#!/usr/bin/env node

var amqp = require('amqplib/callback_api');

// 1. RabbitMQ 서버에 연결한다.
amqp.connect('amqp://localhost', function(err, conn) {
  // 2. 채널을 생성한다.
  conn.createChannel(function(err, ch) {
    // 3. 큐를 생성하고 채널에 등록한다.
    var q = 'hello';

    ch.assertQueue(q, {durable: false});
    console.log(" [*] Waiting for messages in %s. To exit press CTRL+C", q);

    // 4. 큐에 메시지가 들어오는 것을 기다린다.
    ch.consume(q, function(msg) {
      console.log(" [x] Received %s", msg.content.toString());
    }, {noAck: true});
  });
});
```



___

### 참고 문헌

RabbitMQ 공식 홈페이지의 튜토리얼을 번역하며 공부한 내용입니다.

- [RabbitMQ tutorial : "Hello World!"](https://www.rabbitmq.com/tutorials/tutorial-one-javascript.html)