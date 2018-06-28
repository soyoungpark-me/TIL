var http = require('http');

//웹 서버 객체 만들기
var server = http.createServer();

/* 1. 포트 설정해서 실행시키기 */
  //웹 서버를 3000번 포트에서 실행시킨다.
  //만들어논 객체의 listen() 메소드를 호출해 특정 포트에서 대기할 수 있도록 설정.
  //서버를 실행시키는 함수 : server.listen
  //server.listen(port, [hostname], [backlog], [callback]);
  //port만 써도 되나봄.
server.listen(3000, function(){
  console.log("웹 서버를 실행합니다. -> 3000번 포트");
});

/* 2. IP와 포트 설정해서 실행시키기 */
  var host = '127.0.0.0';
  var port = 3000;
  server.listen(port, host, function(){
   console.log("웹 서버를 실행합니다.");
  });

/* 3. on() 메소드는? */
  // on 메소드는 이벤트를 처리할 때 사용하는 가장 기본적인 메소드.
  // server.on() 함수를 호출할 때 첫 번째 인자로 이벤트 이름을 전달하고, 두 번째 인자로 콜백함수 전달...
  // connection, request, close 이벤트를 각각 등록해 두면 필요할 때 갖다가 씀.

// 3-1. 웹 브라우저와 같은 클라이언트가 웹 서버에 연결될 때 connection 이벤트가 발생함.

server.on('connection', function(socket){
  var addr = socket.address();
  console.log("클라이언트가 접속했습니다.");
});

// 3-2. 클라이언트가 특정 path로 요청을 보내면 request 이벤트가 발생함.
server.on('request', function(req, res){
  console.log("클라이언트로부터 요청이 들어왔습니다.");
  // console.dir(req);
  // 들어온 요청에 반응해줘야 클라이언트도 뭔가를 볼 수 있다.
  // 요청에 반응하는 res에는 세 가지 메소드가 있다.
  // 3-2-1. writeHead(상태코드, [메시지], [헤더]) : 응답으로 보낼 헤더 생성.
  // 3-2-2. write(내용, [인코딩], [콜백함수]) : 응답 본문에 들어갈 body 데이터를 만드는데, 여러 번 호출될 수 있음.
  // 3-3-3. end(데이터, [인코딩], [콜백함수]) : 클라이언트로 응답을 전송하는데, 데이터가 들어있다면 아 데이터를 포함해 응답 전송.
         //클라이언트로부터 요청이 들어왔을 때 한 번은 호출되어야 응답을 보낸다. 콜백 함수가 있으면 응답한 후에 실행됨.
  res.write("<h1>응답화면 보여주기</h1>");
});

// 3-3. 서버 종료시키기
// server.close([callback])
server.on('close', function(){
  console.log("서버가 종료됩니다.");
});
