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