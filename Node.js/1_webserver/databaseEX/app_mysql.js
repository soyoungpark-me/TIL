/* MySQL 이용하기
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

// MySQL 연결하기
var mysql = require('mysql');
var pool = mysql.createPool({
  connectionLimit: 10,
  host: 'mydb.crhmcbs1peyl.ap-northeast-2.rds.amazonaws.com',
  user: 'ubuntu',
  password: 'qwer1234',
  database: 'myDB',
  debug: false
});

// 로그인 라우팅 함수 ... 데이터베이스의 정보와 비교
router.route("/users/login").post(function(req, res){
  console.log("/users/login 시작");

  var paramId = req.body.id || req.query.id;
  var paramPwd = req.body.pwd || req.query.pwd;

  // 기존과 다르게, DB 내용과 비교해서 일치할 경우에만 로그인 처리를 해야함.
  if(pool){
    authUser(paramId, paramPwd, function(err, rows){
      // 오류 발생 시 클라이언트로 오류 전송
      if(err){
        console.error("사용자 로그인 중 오류 발생 : " + err.stack);
        res.writeHead('200', {'Content-Type':'text/html; charset=utf8'});
        res.write('<h1>사용자 로그인 중 오류 발생!</h1>');
        res.write('<p>' + err.stack + '</p>');
        res.end();

        return;
      }

      if(rows){
        // 실패헀을 때는 null, null로 콜백함수를 호출하고, 성공헀을 땐 null, docs로 콜백함수를 호출한다.
        // docs가 존재할 경우 로그인에 성공했다는 것!
        console.dir(rows);

        var username = rows[0].name;
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
  var paramAge = req.body.age || req.query.age;

  // 데이터베이스 객체가 초기화된 경우 addUser 함수를 호출해 사용자를 추가한다.
  if(pool){
    addUser(paramId, paramPwd, paramName, paramAge, function(err, result){
      // 동일한 ID로 추가하면 에러가 발생하게 된다.
      if(err) {
        console.error("사용자 추가 중 오류 발생 : " + err.stack);

        res.writeHead('200', {'Content-Type':'text/html; charset=utf8'});
        res.write('<h1>사용자 추가 중 오류 발생!</h1>');
        res.write('<p>' + err.stack + '</p>');
        res.end();

        return;
      }

      // 결과 객체를 확인해 추가된 데이터가 있을 때 성공 응답을 전송함.
      if(addUser){
        console.dir(addUser);

        var insertId = result.insertId;
        console.log("추가한 레코드의 아이디 : " + insertId);

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
});

/*===== 사용자 확인하는 함수 생성! =====*/
var authUser = function(id, password, callback){
  console.log("authUser 호출됨.");

  pool.getConnection(function(err, conn){
    if(err){
      if(conn){
        conn.release(); // 반!드!시! 해제해야 함!
      }
      callback(err, null);
      return;
    }
    console.log("데이터베이스 연결 스레드 아이디 : " + conn.threadId);

    var columns = ['id', 'name', 'age'];
    var tablename = 'users';

    // SQL문 실행!
    var exec = conn.query("select ?? from ?? where id = ? and password = ?",
        [columns, tablename, id, password], function(err, rows){
          conn.release(); // 반!드!시! 해제해야 함!
          console.log("실행 대상 SQL : " + exec.sql);

          if(rows.length > 0){
            console.log("아이디 [%s], 패스워드 [%s]가 일치하는 사용자를 찾았습니다");
            callback(null, rows);
          }else{
            console.log("일치하는 사용자를 찾지 못했습니다.");
            callback(null, null);
          }
        });
  });
}

/*===== 사용자 추가하는 함수 생성! =====*/
var addUser = function(id, password, name, age, callback){
  console.log("addUser 호출됨.");

  // 커넥션 풀에서 연결 객체를 가져옴!
  // getConnection 메소드를 호출해서 가져올 수 있음.
  // 두번째 인자 conn에 연결 객체가 담기게 됨. 연결 객체에는 query 메소드가 있어서 SQL문을 실행할 수 있음!
  pool.getConnection(function(err, conn){
    if(err){
      if(conn){
        conn.release(); // 반!드!시! 해제해야 함!
      }

      callback(err, null);
      return;
    }
    console.log("데이터베이스 연결 스레드 아이디 : " + conn.threadId);

    // 데이터를 객체로 만든다.
    var data = {id:id, name:name, age:age, password:password};

    // SQL문 실행!
    var exec = conn.query('insert into users set ?', data, function(err, result){
      conn.release(); // 반!드!시! 해제해야 함!
      console.log("실행 대상 SQL : " + exec.sql);

      if(err){
        console.log("SQL을 실행하던 중 오류가 발생했습니다.");
        console.dir(err);

        callback(err, null);
        return;
      }

      callback(null, result);
    });
  });
}

// 라우터 객체 등록
app.use('/', router);

module.exports = app;
