var list = function(req, res){
  console.log("user 모듈 안의 list 호출됨.");

  // 데이터베이스 객체 참조
  var database = req.app.get('database');

  // 데이터베이스 객체가 초기화된 경우
  if(database.db){
    database.UserModel.findAll(function(err, result){
      // 오류가 발생했을 때 클라이언트로 오류 전송
      if(err){
        console.log("사용자 리스트 검색 중 오류 발생  : " + err.stack);

        res.writeHead('200', {'Content-Type':'text/html; charset=utf8'});
        res.write('<h1>사용자 리스트 검색 중 오류 발생</h1>');
        res.write('<p>' + err.stack + '</p>');
        res.end();
        return;
      }

      if(result){
        console.dir(result);

        res.writeHead('200', {'Content-Type':'text/html; charset=utf8'});
        res.write(JSON.stringify(result));
        res.end();
      }else{
        res.writeHead('200', {'Content-Type':'text/html; charset=utf8'});
        res.write('<h1>사용자 리스트 검색 실패</h1>');
        res.end();
      }
    });
  }else{
    res.writeHead('200', {'Content-Type':'text/html; charset=utf8'});
    res.write('<h1>데이터베이스 연결 실패</h1>');
    res.end();
  }
}

module.exports.list = list;
