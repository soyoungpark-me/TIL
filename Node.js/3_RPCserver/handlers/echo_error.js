// echo 오류를 테스트하는 함수
var echo_error = function(params, callback){
  console.log("JSON-RPC의 echo_error가 호출되었습니다.");
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

  var output = "Success";
  callback(null, output);
};

module.exports = echo_error;
