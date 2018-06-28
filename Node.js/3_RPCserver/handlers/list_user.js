// 사용자 리스트를 조회하는 함수
var list_user = function(params, callback){
  console.log("JSON-RPC의 list_user가 호출되었습니다.");
  console.dir(params);

  // 데이터베이스 객체 참조하기
  var database = global.database;
  if(database){
    console.log("JSON-RPC에서 database 객체를 참조합니다.");
  }else{
    console.log("JSON-RPC에서 databaes 객체를 참조할 수 없습니다.");
    callback({
      code : 410,
      message : "database 객체 참조 불가"
    }, null);
    return;
  }

  if(database.db){
    // 1. 모든 사용자 검색하기
    database.UserModel.findAll(function(err, result){
      if(result){
        console.log("결과물 문서 데이터의 개수 : %d", result.length);

        var output = [];
        for(var i=0; i<result.length; i++){
          var curId = result[i]._doc.id;
          var curName = result[i]._doc.name;
          output.push({id : curId, name : curName});
        }

        console.dir(output);
        callback(null, output);
      }
    });
  } else {
		callback({
      code: 410,
      message: '데이터베이스 연결  실패'
    }, null);
	}
}

module.exports = list_user;
