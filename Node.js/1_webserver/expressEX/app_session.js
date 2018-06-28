/* 세션 이용하기
 *
 * 1. 먼저 GET 방식으로 리스트 요청
 * 2. user 이름으로 된 세션이 있는지 체크.
 * 3. 만약 저장된 세션이 있으면 리스트 리턴, 없다면 로그인 화면으로 리다이렉트.

 * 4. 로그인에 성공하면 user 세션을 만들어 저장.
 * 5. 클라이언트에 로그인 성공 페이지 리턴.
 * 6. 로그아웃을 한 이후에는 user 세션 파기.
 *
 */

var express = require('express');
var http = require('http');
var path = require('path');

// Express의 미들웨어 불러오기
var bodyParser = require('body-parser');
var static = require('serve-static');
var cookieParser = require('cookie-parser');
var expressSession = require('express-session');

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
app.use(expressSession({
  secret:'my key',
  resave:true,
  saveUninitialized:true
}));

app.use('/public', static(path.join(__dirname, 'public')));

// 라우터를 사용해 라우팅 함수 등록
var router = express.Router();

/* product 리스트를 보유주는 함수 */
router.route('/process/product').get(function(req, res){
  console.log("/process/product 호출");

  // 세션 중 user가 있는지 체크. 유무에 따라 리다이렉트 화면이 달라짐.
  if(req.session.user){
    res.redirect('/public/product.html');
  }else{
    res.redirect('/public/login.html');
  }
});

/* 로그인 함수 */
router.route('/users/login').post(function(req, res){
  console.log("/users/login 시작");

  var paramId = req.body.id || req.query.id;
  var paramPwd = req.body.pwd || req.query.pwd;

  if(req.session.user){
    // 이미 로그인되어 세션이 열렸을 경우에는 여기에 들어오지 않는다!
    console.log("이미 로그인되어 상품 페이지로 이동합니다.");

    res.redirect('/public/product.html');
  }else{
    // 로그인되지 않아 세션이 없다면 여기에 들어온다!
    // 새로 세션을 저장한다!
    req.session.user = {
      id: paramId,
      name: '박소영',
      authorized: true
    };

    res.writeHead('200', {'Content-Type':'text/html; charset=utf8'});
    res.write('<h1>로그인 성공!</h1>');
    res.write('<p>Param id : ' + paramId + '</p></br>');
    res.write('</br></br><a href = "/process/product">상품 페이지로 이동하기</a>');
    res.end();
  }
});

/* 로그아웃 함수 */
router.route('/users/logout').get(function(req, res){
  console.log("/users/logout 시작");

  if(req.session.user){
    //로그인이 되었을 경우 여기도 들어온다.
    console.log("로그아웃합니다.");

    req.session.destroy(function(err){
      if(err){throw err;} //에러가 발생할 경우 에러 처리

      console.log("세션을 삭제하고 로그아웃했습니다.");
      res.redirect('/public/login.html');
    });
  }else{
    //로그인이 되지 않았을 경우엔 로그인 화면으로 이동시킨다.
    console.log("아직 로그인되어있지 않습니다.");

    res.redirect('/public/login.html');
  }
});
app.use('/', router);

// Express 서버 시작
http.createServer(app).listen(app.get('port'), function(){
  console.log('Express server listening on port ' + app.get('port'));
});
