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