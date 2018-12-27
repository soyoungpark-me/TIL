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