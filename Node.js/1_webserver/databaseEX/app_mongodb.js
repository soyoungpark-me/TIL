/* 몽고디비 이용하기
 *
 * 1. 먼저 몽고디비를 쓸 수 있도록 모듈 호출.
 * 2. 모듈을 불러들이면 객체 안에 들어있는 MongoClient 속성을 참고할 수 있음.
 * 3. 이 객체를 이용해 몽고디비를 쓸 수 있음!

 * 4. connectDB 함수를 만들어 몽고디비 데이터베이스에 연결되도록 함.
 * 5. 데이터베이스에 연결할 때 필요한 연결 정보는 문자열로 정의
 *    -> mongodb://%IP정보%:%포트정보%/%데이터베이스이름%
 *
 * 순서를 정리하면 : mongoDB에 연결 > 컬렉션 참조 > 문서 찾기
 *
 */

var express = require('express');
var http = require('http');
var path = require('path');
var logger = require('morgan');

// Express의 미들웨어 불러오기
var bodyParser = require('body-parser');
var static = require('serve-static');
var cookieParser = require('cookie-parser');
var expressSession = require('express-session');

var app = express();

// view engine setup
app.set('views', path.join(__dirname, 'views'));
app.set('view engine', 'jade');

app.set('port', process.env.PORT || 3000);
app.use(logger('dev'));
app.use(bodyParser.json());
app.use(bodyParser.urlencoded({ extended: false }));
app.use(cookieParser());
app.use(express.static(path.join(__dirname, 'public')));

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

// 세션 설정
app.use(expressSession({
  secret: 'my key',
  resave: true,
  saveUninitialized: true
}));

/*===== 여기부터 추가한 코드 =====*/

// 몽고디비 모듈 사용
var mongo = require('mongodb');
var MongoClient = require('mongodb').MongoClient;
var database;

// 로그인 라우팅 함수 ... 데이터베이스의 정보와 비교
router.route("/users/login").post(function(req, res){
  console.log("/users/login 시작");

  var paramId = req.body.id || req.query.id;
  var paramPwd = req.body.pwd || req.query.pwd;

  // 기존과 다르게, DB 내용과 비교해서 일치할 경우에만 로그인 처리를 해야함.
  if(database){
    authUser(database, paramId, paramPwd, function(err, docs){
      if(err) throw err;

      if(docs){
        // 실패헀을 때는 null, null로 콜백함수를 호출하고, 성공헀을 땐 null, docs로 콜백함수를 호출한다.
        // docs가 존재할 경우 로그인에 성공했다는 것!
        console.dir(docs);

        var username = docs[0].name;
        res.writeHead('200', {'Content-Type':'text/html; charset=utf8'});
        res.write('<h1>로그인 성공!</h1>');
        res.write('<p>사용자 아이디 : ' + paramId + '</p></br>');
        res.write('<p>사용자 이름 : ' + username + '</p></br>');
        res.write('</br></br><a href = "/login.html">다시 로그인하기</a>');
        res.end();
      }else{
        // 로그인 실패!
        res.writeHead('200', {'Content-Type':'text/html; charset=utf8'});
        res.write('<h1>로그인 실패!</h1>');
        res.write('</br></br><a href = "/login.html">다시 로그인하기</a>');
        res.end();
      }
    });
  }else{
    // 데이터베이스 연결 자체에 실패했을 때 이리로 온다.
    res.writeHead('200', {'Content-Type':'text/html; charset=utf8'});
    res.write('<h1>데이터베이스 연결 실패!</h1>');
    res.end();
  }
});

// 회원가입 라우팅 함수
router.route("/users/signup").post(function(req, res){
  console.log("/users/signup 호출됨.");

  var paramId = req.body.id || req.query.id;
  var paramPwd = req.body.pwd || req.query.pwd;
  var paramName = req.body.name || req.query.name;

  // 데이터베이스 객체가 초기화된 경우 addUser 함수를 호출해 사용자를 추가한다.
  if(database){
    addUser(database, paramId, paramPwd, paramName, function(err, result){
      if(err) throw err;

      // 결과 객체를 확인해 추가된 데이터가 있을 때 성공 응답을 전송함.
      if(result && result.insertedCount > 0){
        console.dir(result);

        res.writeHead('200', {'Content-Type':'text/html; charset=utf8'});
        res.write('<h1>사용자 추가 성공!</h1>');
        res.end();
      }else{
        res.writeHead('200', {'Content-Type':'text/html; charset=utf8'});
        res.write('<h1>사용자 추가 실패!</h1>');
        res.end();
      }
    });
  }else{
    // 데이터베이스 연결 자체에 실패했을 때 이리로 온다.
    res.writeHead('200', {'Content-Type':'text/html; charset=utf8'});
    res.write('<h1>데이터베이스 연결 실패!</h1>');
    res.end();
  }

});

/*===== 서버 시작 =====*/
http.createServer(app).listen(app.get('port'), function(){
  console.log("서버가 시작되었습니다. -> " + app.get('port'));

  // 하단에 만들어 놓은 함수로 데이터베이스 연결
  connectDB();
});

/*===== 데이터베이스에 연결하는 함수 생성! =====*/
function connectDB(){
  // 데이터베이스 연결 정보
  var databaseUrl = 'mongodb://localhost:27017/local'

  // 데이터베이스에 연결!
  // 첫 번째 파라미터로 연결 URL을, 두 번째 파라미터로 콜백 함수 전달.
  // 데이터베이스가 정상적으로 연결되면 db 객체가 전달됨!
  MongoClient.connect(databaseUrl, function(err, db) {
    if(err) throw err;

    console.log("데이터베이스에 연결되었습니다 : " + databaseUrl);

    // 데이터베이스 변수에 할당
    database = db;

    /* 오류 해결 */
    /* To access the correct object, you need to reference your database name, for me that was by doing */
    /* 버전을 2.2.33으로 down해야 해결되나봄. */
  });
}

/*===== 사용자 확인하는 함수 생성! =====*/
var authUser = function(database, id, password, callback){
  console.log("authUser 호출됨.");

  // users 컬렉션 참조
  var users = database.collection('users');

  // 아이디와 비밀번호를 사용해 검색!
  users.find({"id": id, "password": password}).toArray(function(err, docs){
    // 주어진 걸 찾은 다음에 결과를 docs에 담아서 리턴
    if(err){ //에러가 발생했을 경우에는 그냥 리턴...
      callback(err, null);
      return;
    }

    if(docs.length > 0){
      // 배열의 크기가 0보다 큰 경우 일치하는 row가 있다는 것!
      console.log("아이디 [%s], 비밀번호 [%s]가 일치하는 사용자를 찾았습니다.", id, password);
      callback(null, docs);
    }else{ // 찾기 실패...
      console.log("아이디 [%s], 비밀번호 [%s]가 일치하는 사용자를 찾지 못했습니다.", id, password);
      callback(null, null);
    }
  })
}

/*===== 사용자 추가하는 함수 생성! =====*/
var addUser = function(database, id, password, name, callback){
  console.log("addUser 호출됨.");

  // users 컬렉션 참조
  var users = database.collection('users');

  // 아이디, 비밀번호, 이름을 사용해 새로 신규 유저 추가하기
  users.insertMany([{"id":id, "password":password, "name":name}], function(err, result){
    if(err){
      // 오류가 발생했을 때 콜백 함수를 호출하면서 오류 객체를 전달함
      callback(err, null);
      return;
    }

    // 오류가 발생하지 않은 경우, 콜백 함수를 호출하면서 결과 객체를 전달함
    // 결과 객체의 insertedCount 속성은 추가된 레코드의 개수를 알려준다!
    if(result.insertedCount > 0){
      console.log("사용자 레코드가 추가되었습니다. : " + result.insertedCount);
    }else{
      console.log("추가된 레코드가 없습니다.");
    }

    callback(null, result);
  });
}

// 라우터 객체 등록
app.use('/', router);

module.exports = app;
