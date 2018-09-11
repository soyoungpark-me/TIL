#!/usr/bin/env node

var amqp = require('amqplib/callback_api');

amqp.connect('amqp://localhost', function(err, conn) {
      // 먼저 rabbitMQ 서버에 연결하고,
  conn.createChannel(function(err, ch) {
      // 채널을 생성하고 시작한다!

    var ex = 'direct_logs';
      // 늘 그렇듯, exchange부터 먼저 생성한다.
      
    var args = process.argv.slice(2);
    var msg = args.slice(1).join(' ') || 'Hello World!';
      // 전송할 메시지의 내용이 된다.

    var severity = (args.length > 0) ? args[0] : 'info';
      // args에 따라서 바인딩 키를 생성한다.

    ch.assertExchange(ex, 'direct', {durable: false});
      // fanout 타입이 아니라 direct 타입으로 exchange를 생성한다.

    ch.publish(ex, severity, new Buffer(msg));
      // 메시지를 publish 할 때, 
      // log 심각도(? severity)를 routing key로 사용하게 된다.
      // severity : info, warning, error 중 하나.

    console.log(" [x] Sent %s: '%s'", severity, msg);
  });

  setTimeout(function() { conn.close(); process.exit(0) }, 500);
});

/*  
    ./emit_log_direct.js error "Run. Run. Or it will explode."
    error 메시지를 emit한다.
*/