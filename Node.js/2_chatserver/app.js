var express = require('express');
var http = require('http');
var path = require('path');
var logger = require('morgan');

// Express의 미들웨어 불러오기
var bodyParser = require('body-parser');
var static = require('serve-static');
var cookieParser = require('cookie-parser');
var expressSession = require('express-session');

// 에러 핸들러 모듈 사용
var expressErrorHandler = require('express-error-handler');

// Session 미들웨어 불러오기
var expressSession = require('express-session');

var flash = require('connect-flash');

// 모듈로 분리한 설정 파일 불러오기
var config = require('./config/config');
// 모듈로 분리한 데이터베이스 파일 불러오기
var database = require('./config/database/database');
// 모듈로 분리한 라우팅 파일 불러오기
var route_loader = require('./routes/route_loader');

// Socket.IO 사용
var socketio = require('socket.io');

// cors 사용 - 클라이언트에서 ajax로 요청 시 CORS(다중 서버 접속) 지원
var cors = require('cors');

var app = express();


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

// 세션 설정
app.use(expressSession({
  secret: 'my key',
  resave: true,
  saveUninitialized: true
}));

//클라이언트에서 ajax로 요청 시 CORS(다중 서버 접속) 지원
app.use(cors());

//라우팅 정보를 읽어들여 라우팅 설정
var router = express.Router();
route_loader.initRoutes(app, router);

// 에러 핸들링
var errorHandler = require('errorhandler');
var expressErrorHandler = require('express-error-handler');
var errorHandler = expressErrorHandler({
  static: {
    '404': './public/404.html'
  }
});

//확인되지 않은 예외 처리 - 서버 프로세스 종료하지 않고 유지함
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

// Express 서버 시작
var server = http.createServer(app).listen(app.get('port'), function(){
    console.log('서버가 시작되었습니다. 포트 : ' + app.get('port'));

    // 데이터베이스 초기화
    database.init(app, config);
});

// socket.io 모듈을 사용해 클라이언트가 보내온 요청을 처리하기 위해 listen() 메소드 호출
// listen() 메소드를 호출하면서 웹 서버 객체를 파라미터로 전달하면 웹 소켓으로 들어오는 요청을 처리할 수 있음.
var io = socketio.listen(server);
console.log("socket.io 요청을 받아들일 준비가 되었습니다.");

// 로그인 아이디 매핑.
// 얘를 connection 안에 넣으면 새로 만들 때마다 맵이 초기화되기 때문에 밖에 놔야함!
var login_ids = {};

// 클라이언트가 연결되었을 때 이벤트 처리
io.sockets.on('connection', function(socket){
  console.log("connection info : ", socket.request.connection._peername);

  // 소켓 객체에 접속한 클라이언트의 Host, Port 정보를 속성으로 추가함
  socket.remoteAddress = socket.request.connection._peername.address;
  socket.remotePort = socket.request.connection._peername.port;

  // 'message' 이벤트를 받았을 때 처리
  socket.on('message', function(message){
    console.log("message 이벤트를 받았습니다.");
    console.dir(message);

    if(message.recepient == "ALL"){
      // 나를 포함한 모든 클라이언트에게 메시지 전달
      console.log("나를 포함한 모든 클라이언트에게 message 이벤트를 전송합니다.");
      io.sockets.emit('message', message);
    }else{
      // 일대일 채팅 대상에게 메시지 전달
      if(message.command == 'chat'){
        if(login_ids[message.recepient]){
          // login_ids 중에 recepient가 존재할 때 이리로 들어온다.
          // 상대방 ID를 찾아서 메시지를 전송함!
          // sockets.connected 객체에 들어 있는 소켓들은 제각각 소켓 아이디를 가지고 있음!
          io.sockets.connected[login_ids[message.recepient]].emit('message', message);

          // 응답 메시지 전송
          sendResponse(socket, 'message', '200', '메시지를 전송했습니다.');
        }else{
          // 상대방 ID를 찾지 못한 경우. 메시지를 전송하지 못한다.
          // 응답 메시지 전송
          sendResponse(socket, 'login', '404', '상대방의 로그인 ID를 찾을 수 없습니다.');
        }
      }else if(message.command == 'groupchat'){
        // 방에 들어 있는 모든 사용자에게 메시지 전달
        io.sockets.in(message.recepient).emit('message', message);

        // 응답 메시지 전송
        sendResponse(socket, 'message', '200', '방 ['
            + message.recepient + ']의 모든 사용자들에게 메시지를 전송했습니다.');
      }
    }
  });

  // 'login' 이벤트를 받았을 때 처리
  socket.on('login', function(login){
    console.log("login 이벤트를 받았습니다.");
    console.dir(login);

    // 기존 클라이언트 ID가 없으면 클라이언트 ID를 새로 저장함.
    console.log("접속한 소켓의 ID : " + socket.id);
    login_ids[login.id] = socket.id;
    socket.login_id = login.id;

    console.log("접속한 클라이언트 ID 개수 : %d", Object.keys(login_ids).length);

    // 응답 메시지 전송
    sendResponse(socket, 'login', '200', '로그인되었습니다.');
  });

  // 'logout' 이벤트를 받았을 때 처리
  socket.on('logout', function(logout){
    console.log("logout 이벤트를 받았습니다.");

    if(socket.login_id){
      login_ids[logout.id] = undefined;
      delete login_ids[logout.id];

      socket.login_id = null;

      console.log("접속한 클라이언트 ID 개수 : %d", Object.keys(login_ids).length);

      // 응답 메시지 전송
      sendResponse(socket, 'logout', '200', '로그인되었습니다.');
    }else{
      // 응답 메시지 전송
      sendResponse(socket, 'logout', '404', '먼저 로그인을 해주세요.');
    }
  });

  // room 이벤트를 받았을 때 처리
  socket.on('room', function(room){
    console.log("room 이벤트를 받았습니다.");
    console.dir(room);

    if(room.command == 'create'){
      if(io.sockets.adapter.rooms[room.roomId]){
        // 방이 이미 만들어져 있는 경우
        console.log("방이 이미 만들어져 있습니다.");
      }else{
        console.log("방을 새로 만듭니다.");

        socket.join(room.roomId);

        var curRoom = io.sockets.adapter.rooms[room.roomId];
        curRoom.id = room.roomId;
        curRoom.name = room.roomName;
        curRoom.owner = room.roomOwner;
      }
    }else if(room.command == 'update'){
      var curRoom = io.sockets.adapter.rooms[room.roomId];
      curRoom.id = room.roomId;
      curRoom.name = room.roomName;
      curRoom.owner = room.roomOwner;
    }else if(room.command == 'delete'){
      socket.leave(room.roomId);

      if(io.sockets.adapter.rooms[room.roomIds]){
        // 방이 만들어져 있는 경우
        delete io.sockets.adapter.rooms[room.roomId];
      }else{
        // 방이 만들어져 있지 않은 경우
        console.log("방이 만들어져 있지 않습니다.");
      }
    }else if(room.command == 'join'){
      socket.join(room.roomId);

      // 응답 메시지 전송
      sendResponse(socket, 'room', '200', '방에 입장했습니다.');
    }else if(room.command == 'leave'){
      socket.leave(room.roomId);

      // 응답 메시지 전송
      sendResponse(socket, 'room', '200', '방에서 나갔습니다.');
    }

    // 클라이언트로 방 리스트 보냄
    var roomList = getRoomList();
    var output = {command : 'list', rooms : roomList};
    console.log("클라이언트로 보낼 데이터 : " + JSON.stringify(output));

    io.sockets.emit('room', output);
  })
});

function sendResponse(socket, command, code, message){
  var statusObj = {command : command, code : code, message : message};
  socket.emit('response', statusObj);
};

// 기본 방 (default room) : join 메소드가 아니라 처음으로 만들어져 있던 방.
// list로 출력할 때는 이 default room을 제외하고 쏴줘야 함.
// default room > 방의 이름과 그 안에 들어 있는 속성의 이름이 같은 경우.
function getRoomList(){
  console.dir(io.sockets.adapter.rooms);

  var roomList = [];

  Object.keys(io.sockets.adapter.rooms).forEach(function(roomId){
    // rooms 중 각각의 방에 대해서 처리함.

    console.log("current room id : " + roomId);
    var outRoom = io.sockets.adapter.rooms[roomId];

    // 속성을 이용해 default room을 찾음!
    var foundDefault = false;
    var index = 0;
    Object.keys(outRoom.sockets).forEach(function(key){
      console.log("# " + index + " : " + key + ', ' + outRoom.sockets[key]);

      if(roomId == key){
        // 여기에 default room이 들어온다.
        foundDefault = true;
        console.log("default room을 찾았습니다.");
      }
      index++;
    });

    if(foundDefault){
      // 기본 방을 찾은 후에는 outRoom들을 하나씩 roomList에 추가한다.
      roomList.push(outRoom);
    }
  });

  console.log('[Room List]');
  console.dir(roomList);
  return roomList;
}
