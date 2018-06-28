var express = require('express');
var path = require('path');
var favicon = require('serve-favicon');
var logger = require('morgan');
var cookieParser = require('cookie-parser');
var bodyParser = require('body-parser');
var http = require('http');

var user = require('./routes/users');
var app = express();

// view engine setup
app.set('views', path.join(__dirname, 'views'));
app.set('view engine', 'jade');
app.set('port', process.env.PORT || 3000);

// uncomment after placing your favicon in /public
//app.use(favicon(path.join(__dirname, 'public', 'favicon.ico')));
app.use(logger('dev'));
app.use(bodyParser.json());
app.use(bodyParser.urlencoded({ extended: false }));
app.use(cookieParser());
app.use(express.static(path.join(__dirname, 'public')));

// catch 404 and forward to error handler
app.use(function(req, res, next) {
  var err = new Error('Not Found');
  err.status = 404;
  next(err);
});

// error handler
app.use(function(err, req, res, next) {
  // set locals, only providing error in development
  res.locals.message = err.message;
  res.locals.error = req.app.get('env') === 'development' ? err : {};

  // render the error page
  res.status(err.status || 500);
  res.render('error');
});

/* 몽고디비 데이터베이스 관련 부분 */
var mongo = require('mongodb');
var mongoose = require('mongoose');
var MongoClient = require('mongodb').MongoClient;
var database;   // 데이터베이스 객체 선언
var UserSchema; // 데이터베이스 스키마 객체 선언
var UserModel;  // 데이터베이스 모델 객체 선언

/*===== 데이터베이스에 연결하는 함수 생성! =====*/
function connectDB(){
  // 데이터베이스 연결 정보
  var databaseUrl = 'mongodb://localhost:27017/local'

  // 데이터베이스에 연결!
  console.log("데이터베이스 연결을 시도합니다.");
  mongoose.Promise = global.Promise;
  mongoose.connect(databaseUrl);
  database = mongoose.connection;

  database.on('error', console.error.bind(console, 'mongoose connection error.'));
  database.on('open', function(){
    console.log("데이터베이스에 연결되었습니다. : " + databaseUrl);

    // user 스키마 및 모델 객체 생성
		createUserSchema(database);
  });

  // 연결이 끊어지면 5초 후에 재연결 해야함.
  database.on('disconnected', function(){
    console.log("연결이 끊어졌습니다. 5초 후 다시 연결합니다.");
    setInterval(connectDB, 5000);
  });
};

// user 스키마 및 모델 객체 생성
function createUserSchema(){
  // user_schema.js 모듈 불러오기
  UserSchema = require('./config/database/user_schema').createSchema(mongoose);

  // UserModel 모델 정의
  UserModel = mongoose.model("user3", UserSchema);
  console.log("UserModel 정의함.");

  // init 호출
  user.init(database, UserSchema, UserModel);
};

/* 라우팅 부분 */
//===== 라우팅 함수 등록 =====//

// 라우터 객체 참조
var router = express.Router();

// 4. 로그인 처리 함수를 라우팅 모듈을 호출하는 것으로 수정
router.route("/users/login").post(user.login);

// 5. 사용자 추가 함수를 라우팅 모듈을 호출하는 것으로 수정
router.route("users/signup").post(user.signup);

// 6. 사용자 리스트 함수를 라우팅 모듈을 호출하는 것으로 수정
router.route("/users/list").post(user.list);

// 라우터 객체 등록
app.use('/', router);

/* Express 서버 시작 */
http.createServer(app).listen(app.get('port'), function(){
  console.log('서버가 시작되었습니다. 포트 : ' + app.get('port'));

  // 데이터베이스 연결을 위한 함수 호출
  connectDB();
});

module.exports = app;
