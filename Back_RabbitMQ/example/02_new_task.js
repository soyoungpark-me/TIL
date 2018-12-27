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