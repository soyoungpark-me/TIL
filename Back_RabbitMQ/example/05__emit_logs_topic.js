#!/usr/bin/env node

var amqp = require('amqplib/callback_api');

amqp.connect('amqp://localhost', function(err, conn) {
  conn.createChannel(function(err, ch) {
    var ex = 'topic_logs';
      // exchange를 생성하는 것까지는 동일하다.

    var args = process.argv.slice(2);
    var key = (args.length > 0) ? args[0] : 'anonymous.info';
      // args에 따라서 라우팅 키를 생성한다.

    var msg = args.slice(1).join(' ') || 'Hello World!';
      // 전송할 메시지의 내용이 된다.

    ch.assertExchange(ex, 'topic', {durable: false});
      // direct 타입이 아니라 topic 타입으로 exchange를 생성한다.

    ch.publish(ex, key, new Buffer(msg));
      // 메시지를 publish 할 때, args에 따라 생성된 라우팅 키를 적용한다.

    console.log(" [x] Sent %s: '%s'", key, msg);
  });

  setTimeout(function() { conn.close(); process.exit(0) }, 500);
});

/*
    ./emit_log_topic.js "kern.critical" "A critical kernel error"
    "kern.critical" 타입의 라우팅 키를 가진 메시지를 생성해 전송한다.
*/