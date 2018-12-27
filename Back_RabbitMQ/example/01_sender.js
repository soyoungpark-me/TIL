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