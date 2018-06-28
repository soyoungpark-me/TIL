var http = require('http');

var options = {
  host: 'www.google.com',
  post: 80,
  path: '/'
};

// http의 get 메소드를 이용하면 다른 웹 서버에 요청을 보낼 수 있음.
// 첫번째 인자는 어떤 웹 서버에 접근할 걸지를 알려주는 객체가 들어감.
var req = http.get(options, function(res){
  // 응답 처리하기
  var resData = '';

  // 데이터를 받게 되면 data 이벤트가 발생해 아래 함수가 실행된다.
  res.on('data', function(chunk){
    resData += chunk;
  });

  // 데이터 수신이 끝나면 end 이벤트 발생!
  res.on('end', function(){
    console.log(resData);
  });
});

req.on('error', function(err){
  console.log("오류 발생 : " + err.message);
});
