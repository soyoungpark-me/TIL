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

// 세션 설정
app.use(expressSession({
  secret: 'my key',
  resave: true,
  saveUninitialized: true
}));

// 모듈로 분리한 설정 파일 불러오기
var config = require('./config/config');
// 모듈로 분리한 데이터베이스 파일 불러오기
global.database = require('./config/database/database');
// 모듈로 분리한 라우팅 파일 불러오기
var route_loader = require('./routes/route_loader');
// JsonRpc 핸들러 로딩을 위한 파일 불러오기
var handler_loader = require('./handlers/handler_loader');
// JsonRpc 사용을 위한 jayson 모듈 불러오기
var jayson = require('jayson');

// view engine setup
app.set('views', path.join(__dirname, 'views'));
app.set('view engine', 'ejs');

// 서버 변수 설정 및 static으로 public 폴더 설정
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
// 라우터 객체 등록
app.use('/', router);

// jayson 미들웨어 사용 설정
// JSON-RPC 핸들러 정보를 읽어들여 핸들러 설정
var jsonrpc_api_path = config.jsonrpc_api_path || '/api';
handler_loader.init(jayson, app, jsonrpc_api_path);
console.log('JSON-RPC를 [' + jsonrpc_api_path + '] 패스에서 사용하도록 설정함.');

// Express 서버 시작
// 확인되지 않은 예외 처리 - 서버 프로세스 종료하지 않고 유지함
process.on('uncaughtException', function (err) {
	console.log('uncaughtException 발생함 : ' + err);
	console.log('서버 프로세스 종료하지 않고 유지함.');

	console.log(err.stack);
});

// 프로세스 종료 시에 데이터베이스 연결 해제
process.on('SIGTERM', function () {
    console.log("프로세스가 종료됩니다.");
    app.close();
});
app.on('close', function () {
	console.log("Express 서버 객체가 종료됩니다.");
	if (database.db) {
		database.db.close();
	}
});

// 시작된 서버 객체를 리턴받도록 합니다.
var server = http.createServer(app).listen(app.get('port'), function(){
	console.log('서버가 시작되었습니다. 포트 : ' + app.get('port'));

	// 데이터베이스 초기화
	database.init(app, config);
});
