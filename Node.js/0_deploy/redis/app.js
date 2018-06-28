var express = require('express');
var http = require('http');
var path = require('path');
var logger = require('morgan');

// Express의 미들웨어 불러오기
var bodyParser = require('body-parser');
var static = require('serve-static');
var cookieParser = require('cookie-parser');
var expressSession = require('express-session');

// 레디스 사용하기
var redis = require('redis');
var store = redis.createClient();
var pub = redis.createClient();
var sub = redis.createClient();

var channel_name = 'chat';
sub.on('message', function(channel, dataStr){
  console.log('Redis subscriber received message on channel' + channel);

  // stringdmf JSON으로
  var data = JSON.parse(dataStr);
  console.log('DATA %j', data);
});

sub.subscribe(channel_name);
console.log('redis에 subscribe하였습니다.' + channel_name);

var app = express();

// 세션 설정
app.use(expressSession({
  secret: 'my key',
  resave: true,
  saveUninitialized: true
}));

// 모듈로 분리한 설정 파일 불러오기
var config = require('./config/config');
// 모듈로 분리한 데이터베이스 파일 불러오기
var database = require('./config/database/database');
// 모듈로 분리한 라우팅 파일 불러오기
var route_loader = require('./routes/route_loader');

// view engine setup
app.set('views', path.join(__dirname, 'views'));
app.set('view engine', 'ejs');

//===== 서버 변수 설정 및 static으로 public 폴더 설정  =====//
console.log('config.server_port : %d', config.server_port);
app.set('port', process.env.PORT || 3000);
app.use(express.static(path.join(__dirname, 'public')));
app.use(logger('dev'));
app.use(bodyParser.json());
app.use(bodyParser.urlencoded({ extended: false }));
app.use(cookieParser());

// 에러 핸들링
var errorHandler = require('errorhandler');
var expressErrorHandler = require('express-error-handler');
var errorHandler = expressErrorHandler({
  static: {
    '404': './public/404.html'
  }
});

// 라우터 객체 참조
var router = express.Router();
// 라우팅 정보를 읽어들여 라우팅 설정
route_loader.initRoutes(app, router);

// redis 테스트용 라우터 등록
router.route('/process/setname').post(function(req, res){
  console.log("/process/setname 호출됨.");

  var paramId = req.body.id || req.query.id;
  var paramName = req.body.name || req.query.name;

  store.hset("user", paramId, paramName, redis.print);
  console.log("redis에 사용자를 등록했습니다. : " + paramId + ' -> ' + paramName);

  res.writeHead('200', {'Content-Type':'text/html; charset=utf8'});
  res.write('<h1>서버에서 응답한 결과입니다.</h1>');
  res.write('<div><p>redis에 사용자를 등록했습니다.'+ paramId + ' -> '+ paramName +'</p></div>');
  res.write('<br><br><a href = "/public/setname.html"> 처음으로 돌아가기');
  res.end();
});

router.route('/process/getname').post(function(req, res){
  console.log("process/getname 호출됨.");

  var paramId = req.body.id || req.query.id;

  store.hget("user", paramId, function(err, username){
    if(err) throw err;

    if(username){
      console.log("redis에서 사용자를 찾았습니다. : " + paramId + ' -> ' + username);

      res.writeHead('200', {'Content-Type':'text/html; charset=utf8'});
      res.write('<h1>서버에서 응답한 결과입니다.</h1>');
      res.write('<div><p>redis에서 사용자를 찾았습니다. : ' + paramId + ' -> ' + username + '</p></div>');
      res.write('<br><br><a href = "/public/getname.html"> 처음으로 돌아가기');
      res.end();
    }else{
      res.writeHead('200', {'Content-Type':'text/html; charset=utf8'});
      res.write('<h1>서버에서 응답한 결과입니다.</h1>');
      res.write('<div><p>redis에서 사용자를 찾지 못했습니다. : ' + paramId + '</p></div>');
      res.write('<br><br><a href = "/public/getname.html"> 처음으로 돌아가기');
      res.end();
    }
  })
});

router.route('/process/publish').post(function(req, res){
  console.log("/process/publish 호출됨.");

  var paramSender = req.body.sender || req.query.sender;
  var paramReceiver = req.body.receiver || req.query.receiver;
  var paramContents = req.body.contents || req.query.contents;

  var data = {sender : paramSender, receiver : paramReceiver, contents : paramContents};
  var dataStr = JSON.stringify(data);
  pub.publish('chat', dataStr);

  res.writeHead('200', {'Content-Type':'text/html; charset=utf8'});
  res.write('<h1>서버에서 응답한 결과입니다.</h1>');
  res.write('<div><p>redis로 publish 했습니다. 서버 로그를 확인하세요. : ' + dataStr + '</p></div>');
  res.write('<br><br><a href = "/public/publish.html"> 처음으로 돌아가기');
  res.end();
})
// 라우터 객체 등록
app.use('/', router);

// 패스모트 관련 모듈, 사용 설정
var passport = require('passport');
var flash = require('connect-flash');
var LocalStrategy = require('passport-local').Strategy;
var configPassport = require('./config/passport/passport');
var userPassport = require('./routes/passport');

app.use(passport.initialize()); // 패스포트 초기화
app.use(passport.session());    // 로그인 세션 유지
app.use(flash());               // 플래시 설정
configPassport(app, passport);  // 패스포트 config 초기화
userPassport(app, passport);    // 패스포트 라우터 초기화

// Express 서버 시작
http.createServer(app).listen(process.env.PORT || app.get('port'), function(){
    console.log('서버가 시작되었습니다. 포트 : ' + app.get('port'));

    // 데이터베이스 초기화
    database.init(app, config);
});
