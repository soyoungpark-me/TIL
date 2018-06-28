var login = function(req, res){
  console.log("user 모듈 안에 있는 login이 호출되었습니다.");

  var paramId = req.body.id || req.query.id;
  var paramPwd = req.body.pwd || req.query.pwd;

  // 데이터베이스 객체 참조
	var database = req.app.get('database');

  // 기존과 다르게, DB 내용과 비교해서 일치할 경우에만 로그인 처리를 해야함.
  if(database.db){
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
};

var signup = function(req, res){
  console.log("user 모듈 안에 있는 signup이 호출되었습니다.");

  var paramId = req.body.id || req.query.id;
  var paramPwd = req.body.pwd || req.query.pwd;
  var paramName = req.body.name || req.query.name;

  // 데이터베이스 객체 참조
	var database = req.app.get('database');

  // 데이터베이스 객체가 초기화된 경우 addUser 함수를 호출해 사용자를 추가한다.
  if(database.db){
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
};

var list = function(req, res){
  console.log("user 모듈 안에 있는 list가 호출되었습니다.");

  // 데이터베이스 객체 참조
	var database = req.app.get('database');

  // 데이터베이스 객체가 초기화되었을 경우에 모델 객체의 findAll 메소드를 호출한다.
  if(database.db){
    // 1. 먼저 모든 사용자를 검색함!
    database.UserModel.findAll(function(err, result){
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
};

//사용자를 인증하는 함수 : 아이디로 먼저 찾고 비밀번호를 그 다음에 비교하도록 함
var authUser = function(database, id, password, callback) {
	console.log('authUser 호출됨.');

  database.UserModel.find({"id": id, "password": password}, function(err, result){
    if(err){
      //에러가 발생했을 경우에는 그냥 리턴...
      callback(err, null);
      return;
    }

    console.log("아이디 [%s], 비밀번호 [%s]로 사용자를 검색한 결과...", id, password);
    console.dir(result);

    if(result.length > 0){
      // 결과의 크기가 0보다 큰 경우 일치하는 row가 있다는 것!
      console.log("아이디 [%s], 비밀번호 [%s]가 일치하는 사용자를 찾았습니다.", id, password);
      callback(null, result);
    }else{ // 찾기 실패...
      console.log("아이디 [%s], 비밀번호 [%s]가 일치하는 사용자를 찾지 못했습니다.", id, password);
      callback(null, null);
    }
  });
};

module.exports.login = login;
module.exports.signup = signup;
module.exports.list = list;
