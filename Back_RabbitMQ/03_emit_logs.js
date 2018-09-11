#!/usr/bin/env node

var amqp = require('amqplib/callback_api');

amqp.connect('amqp://localhost', function(err, conn) {
  conn.createChannel(function(err, ch) {
    var ex = 'logs';
      // 채널에 연결된 후, exchange를 생성한다.
    var msg = process.argv.slice(2).join(' ') || 'Hello World!';

    ch.assertExchange(ex, 'fanout', {durable: false});
      // exchange의 타입을 fanout으로 지정한 뒤 연결한다.

    ch.publish(ex, '', new Buffer(msg));
      // 인자 : 전송할 ex이름, 메시지를 보낼 큐 지정, 메시지 내용
      // 큐를 딱히 지정하지 않았으므로 모든 큐에게 보낸다.
      
    console.log(" [x] Sent %s", msg);
  });

  setTimeout(function() { conn.close(); process.exit(0) }, 500);
});