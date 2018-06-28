var http = require('http');
var fs = require('fs');

var server = http.createServer();

server.listen(3000, function(){
  console.log("웹 서버를 실행합니다. -> 3000번 포트");
});

server.on('request', function(req, res){
  console.log("클라이언트로부터 요청이 들어왔습니다.");

  // 여기서부터 파일 입출력 코드 시작.
  var filename = 'faker.png';
  /* 1. 먼저 readFile 함수를 이용해 읽어오고자 하는 이미지를 가져온다. */
    fs.readFile(filename, function(err, data){
      // writeHead 함수를 이용해 HTTP 헤더를 설정한다. 첫 번째 값은 상태 코드, 두 번쨰 값은 JSON으로 전달하는 헤더 내용.
      res.writeHead(200, {"Content-Type" : "image/png"});
      res.write(data);  //write 함수로 데이터 전송!
      res.end();
    });

  /* 2. readFile이 아니라 스트림을 파이프로 연결하는 방법도 있음! */
    // 파일을 스트림 객체로 읽어들인 다음 pipe() 메소드를 이용해 파일에 응답을 보낼 수 있음.
    var infile = fs.createReadStream(filename, {flags: 'r'});
    // 파이프로 연결해 알아서 처리되도록 설정한다.
    infile.pipe(res);

  /* 3. 파일을 버퍼에 담아 두고 일부만 꺼내서 출력하는 방법 */
    var filename = 'faker.png';
    // 먼저 파일을 스트림 객체로 읽어들임.
    var infile = fs.createReadStream(filename, {flags: 'r'});
    var filelength = 0;
    var curlength = 0;

    fs.stat(filename, function(err, stats){
      filelength = stats.size;
    });

    res.writeHead(200, {"Content-Type" : "image/png"});

    infile.on('readable', function(){
      var chunk;
      // chunk는 읽어 들인 데이터.
      // infile.read()한 결과를 chunk에 담아 두고, 이게 null이 아닐 동안 삥삥 돈다.
      while(null !== (chunk = infile.read())){
        console.log("읽어 들인 데이터의 크기 : %d", chunk.length);
        curlength += chunk.length;

        res.write(chunk, 'utf8', function(err){
          console.log("파일 부분 쓰기 완료 : %d, 파일 크기 : %d", curlength, filelength);
          // 현재 크기와 파일 전체 크기를 비교해서 다 쓰면 res.end()를 호출해 읽어 들이는 작업이 끝났음을 알림.
          if(curlength >= filelength){
            // write 메소드가 호출된 이후 end 메소드 호출.
            // 쓰기가 완료된 시점 확인!
            res.end();
          }
        });
      }
  });
});
