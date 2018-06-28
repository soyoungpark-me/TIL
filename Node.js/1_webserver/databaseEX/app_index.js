/* 속성에 인덱스 추가하기
 *
 * 스키마를 만들 때 각 속성의 스키마 타입에는 여러 정보가 들어갈 수 있는데, 인덱스도 그 중 하나!
 * 사용자 스키마를 만들면서 인덱스 정보를 추가할 수 있음.
 *   Ex) name: {type: String, index: 'hashed'}
 * unique 속성 값이 true 일 경우에는 자동으로 인덱스가 만들어짐.
 * 인덱스를 사용하면 검색 속도가 빨리지므로 조회가 필요한 속성에는 인덱스를 만들어 두는 것이 좋다!
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
var mongoose = require('mongoose');
var MongoClient = require('mongodb').MongoClient;
var database;   // 데이터베이스 객체 선언
var UserSchema; // 데이터베이스 스키마 객체 선언
var UserModel;  // 데이터베이스 모델 객체 선언

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

// 사용자 리스트 함수
router.route('/users/list').get(function(req, res){
  console.log("/users/list 호출됨.");

  // 데이터베이스 객체가 초기화되었을 경우에 모델 객체의 findAll 메소드를 호출한다.
  if(database){
    // 1. 먼저 모든 사용자를 검색함!
    UserModel.findAll(function(err, result){
      // 오류가 발생하면 클라이언트로 오류를 전송함.
      if(err){
        console.log("사용자 리스트 조회 중 오류 발생 : "+ err.stack);
        res.writeHead('200', {'Content-Type':'text/html; charset=utf8'});
        res.write('<h1>사용자 리스트 조회 중 오류 발생</h1>');
        res.write('<p>' + err.stack + '</p>');
        res.end();

        return;
      }

      if(result){  // 결과 객체가 있으면 리스트를 전송함.
				console.dir(result);

				res.writeHead('200', {'Content-Type':'text/html;charset=utf8'});
				res.write('<h2>사용자 리스트</h2>');
				res.write('<div><ul>');

				for (var i = 0; i < result.length; i++) {
					var curId = result[i]._doc.id;
					var curName = result[i]._doc.name;
					res.write('<li>#' + i + ' : ' + curId + ', ' + curName + '</li>');
				}

				res.write('</ul></div>');
				res.end();
			}else{  // 결과 객체가 없으면 실패 응답 전송
				res.writeHead('200', {'Content-Type':'text/html;charset=utf8'});
				res.write('<h2>사용자 리스트 조회에 실패했습니다!</h2>');
				res.end();
			}
    });
  }else{  // 데이터베이스 객체가 초기화되지 않은 경우 실패 응답 전송
		res.writeHead('200', {'Content-Type':'text/html;charset=utf8'});
		res.write('<h2>데이터베이스 연결에 실패했습니다!</h2>');
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
  console.log("데이터베이스 연결을 시도합니다.");
  mongoose.Promise = global.Promise;
  mongoose.connect(databaseUrl);
  database = mongoose.connection;

  database.on('error', console.error.bind(console, 'mongoose connection error.'));
  database.on('open', function(){
    console.log("데이터베이스에 연결되었습니다. : " + databaseUrl);

    // 스키마 정의하기 (인덱스 추가한 버전)
    UserSchema = mongoose.Schema({
      id: {type: String, required: true, unique: true},
      password: {type: String, required: true},
      name: {type: String, index: 'hashed'},
      age: {type: Number, 'default' : -1},
      created_at: {type: Date, index: {unique: false}, 'default': Date.now},
      updated_at: {type: Date, index: {unique: false}, 'default': Date.now}
    });
    // 스키마에 static 메소드 추가
    UserSchema.static('findById', function(id, callback){
      return this.find({id: id}, callback);
    });

    UserSchema.static('findAll', function(callback){
      return this.find({}, callback);
    });
    console.log("UserSchema 정의함!");

    // UserModel 정의하기
    // 첫 번째 인자 users는 컬렉션을 지정하는 역할을 하고, 두 번째 인자는 스키마를 지정하는 역할을 한다.
    // 이렇게 모델 객체를 만들면 모델 객체에 지정한 users라는 이름은 DB의 users 컬렉션과 매칭된다.
    UserModel = mongoose.model("users2", UserSchema);
    console.log("UserModel 정의함!");
  });

  // 연결이 끊어지면 5초 후에 재연결 해야함.
  database.on('disconnected', function(){
    console.log("연결이 끊어졌습니다. 5초 후 다시 연결합니다.");
    setInterval(connectDB, 5000);
  });
};

/*===== 사용자 확인하는 함수 생성! =====*/
var authUser = function(database, id, password, callback){
  console.log("authUser 호출됨.");

  // 아이디와 비밀번호를 사용해 검색!
  // 인덱스를 이용하도록 변경함.
  // 1. 먼저 아이디를 이용해 검색한다.
  UserModel.findById(id, function(err, result){
    if(err){
      callback(err, null);
      return;
    }

    console.log("아이디 [%s]로 사용자를 검색한 결과", id);
    console.dir(result);

    if(result.length > 0){
      console.log("해당 아이디와 일치하는 사용자를 찾았습니다");

      // 2. 비밀번호 확인
      // _doc 속성은 각 문서 객체의 정보를 담고 있다! -> 그 안에 있는 password 속성 값을 확인할 수 있음.
      if(result[0]._doc.password === password){
        console.log("비밀번호가 일치합니다!");
        callback(null, result);
      }else{
        console.log("비밀번호가 일치하지 않습니다.");
        callback(null, null);
      }
    }else{
      console.log("해당 아이디와 일치하는 사용자가 없습니다.");
      callback(null, null);
    }
  });
};

/*===== 사용자 추가하는 함수 생성! =====*/
var addUser = function(database, id, password, name, callback){
  console.log("addUser 호출됨.");

  // save의 경우에는 모델의 인스턴스를 생성해줘야 한다!!!
  // 인자로 끌어온 인자들이 고대로 인스턴스에 전달되므로 user.save만 해주면 됨!
  var user = new UserModel({"id": id, "password": password, "name": name});

  // save() 메소드로 저장하기!
  user.save(function(err){
    if(err){
      callback(err, null);
      return;
    }

    console.log("사용자 데이터가 새로 추가되었습니다.");
    callback(null, user);
  });
};

// 라우터 객체 등록
app.use('/', router);

module.exports = app;
