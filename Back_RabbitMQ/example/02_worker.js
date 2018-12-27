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