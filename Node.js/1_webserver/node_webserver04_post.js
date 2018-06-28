var http = require('http');

var opts = {
  host: 'www.google.com',
  port: 80,
  method: 'POST',
  path: '/',
  headers: {}
};

var resData = '';

// POST 방식을 처리할 때는 request() 메소드를 사용함. get()과는 다른 점이 있는데...
// 요청을 보내려면 요청 헤더와 body 본문을 모두 직접 설정해줘야 함.

var req = http.request(opts, function(res){
  // 응답 처리하기
  res.on('data', function(chunk){
    resData += chunk;
  });

  res.on('end', function(){
    console.log(resData);
  });
});

// 아래 부분을 통해 헤더값 직접 설정.
opts.headers['Content-Type'] = 'application/x-www-form-unlencoded';
req.data = "q=actor";
opts.headers['Content-Length'] = req.data.length;

req.on('error', function(err){
  console.log("오류 발생 : " + err.message);
});

// 요청 전송하기
req.write(req.data);
req.end();
