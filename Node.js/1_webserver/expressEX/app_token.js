var express = require('express');
var http = require('http');
var path = require('path');

// Express의 미들웨어 불러오기
var bodyParser = require('body-parser');
var static = require('serve-static');

// 익스프레스 객체 생성
var app = express();

// 기본 속성 설정
app.set('port', process.env.PORT || 3000);

// body-parser를 이용해 application/x-www-form-urlencoded 파싱
app.use(bodyParser.urlencoded({ extended: false }))

// body-parser를 이용해 application/json 파싱
app.use(bodyParser.json())

app.use('/public', static(path.join(__dirname, 'public')));

// 라우터를 사용해 라우팅 함수 등록
var router = express.Router();

router.route('/process/users/:id').get(function(req, res) {
	console.log('/process/users/:id 처리함.');

  // URL 파라미터 확인
	var paramId = req.params.id;

	console.log('/process/users와 토큰 %s를 이용해 처리함.', paramId);

	res.writeHead('200', {'Content-Type':'text/html;charset=utf8'});
	res.write('<h1>Express 서버에서 응답한 결과입니다.</h1>');
	res.write('<div><p>Param id : ' + paramId + '</p></div>');
	res.end();

  // 이렇게 토큰을 사용하면 리스트 중 특정 정보를 id 값으로 조회하는 데 편리함.
});

app.use('/', router);

// 등록되지 않은 패스에 대해 페이지 오류 응답
app.all('*', function(req, res) {
  //404 status일 경우 아래 내용 return.
	res.status(404).send('<h1>ERROR - 페이지를 찾을 수 없습니다.</h1>');
});

// Express 서버 시작
http.createServer(app).listen(app.get('port'), function(){
  console.log('Express server listening on port ' + app.get('port'));
});
