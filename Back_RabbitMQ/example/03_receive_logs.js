#!/usr/bin/env node

var amqp = require('amqplib/callback_api');

amqp.connect('amqp://localhost', function(err, conn) {
  conn.createChannel(function(err, ch) {
    var ex = 'logs';

    ch.assertExchange(ex, 'fanout', {durable: false});
      // exchange를 fanout 타입으로 생성해 연결한다.

    ch.assertQueue('', {exclusive: true}, function(err, q) {
      // exclusive를 true로 설정해서 temporary queue를 사용하도록 한다.
      // 여기서는 q가 임시로 만든 큐를 리턴해준다.
    
      console.log(" [*] Waiting for messages in %s. To exit press CTRL+C", q.queue);
      
      ch.bindQueue(q.queue, ex, '');
        // 리턴 받은 큐와 채널에서 생성한 exchange를 bind 해준다.
        // 인자 : 큐 이름, ex 이름, 라우팅
        // 세번째 인자가 없으므로 해당 큐는 ex에서 오는 모든 메시지를 받는다.

      ch.consume(q.queue, function(msg) {
        console.log(" [x] %s", msg.content.toString());
      }, {noAck: true});
    });
  });
});