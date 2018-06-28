var express = require('express');
var http = require('http');
var path = require('path');

// Express의 미들웨어 불러오기
var bodyParser = require('body-parser');
var static = require('serve-static');
var cookieParser = require('cookie-parser');

// 익스프레스 객체 생성
var app = express();

// 기본 속성 설정
app.set('port', process.env.PORT || 3000);

// body-parser를 이용해 application/x-www-form-urlencoded 파싱
app.use(bodyParser.urlencoded({ extended: false }))

// body-parser를 이용해 application/json 파싱
app.use(bodyParser.json());

//Try to install cookieParser separately. It because express no longer contains inside cookieParser
//app.use(express.cookieParser());
app.use(cookieParser());

app.use('/public', static(path.join(__dirname, 'public')));

// 라우터를 사용해 라우팅 함수 등록
var router = express.Router();

router.route('/process/showCookie').get(function(req, res){
  console.log("/process/showCookie 호출");
  // 클라이언트의 쿠키 정보는 cookies 객체에 들어 있다.
  // 이 쿠키 객체를 클라이언트에 그대로 전달.
  res.send(req.cookies);
});

router.route('/process/setUserCookie').get(function(req, res){
  console.log("/process/setUserCookie 호출");

  // 아래 내용으로 쿠키 생성!
  res.cookie('user', {
    id: '3457soso',
    name: '소영',
    authorized: true
  });

  // redirect로 응답하기
  res.redirect('/process/showCookie');
});

app.use('/', router);

// Express 서버 시작
http.createServer(app).listen(app.get('port'), function(){
  console.log('Express server listening on port ' + app.get('port'));
});
