// 더하기 함수
var add = function(params, callback){
  console.log("JSON-RPC의 add가 호출되었습니다.");
  console.dir(params);

  // 파라미터 체크하기
  if(params.length < 2){
    // 파라미터의 개수가 부족할 경우
    callback({
      code : 400,
      message : '파라미터 개수가 부족합니다.'
    }, null);
    return;
  }

  var a = params[0];
  var b = params[1];
  var output = a + b;

  callback(null, output);
};

module.exports = add;
