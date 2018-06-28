// params : 클라이언트로부터 전달 받은 것이며 배열 객체로 되어 있음.
// callback : 함수로, 클라이언트에 응답을 보낼 때 사용.
  // callback 함수에 들어가는 두 개의 파라미터...
  // 첫 번째 : 오류 전달을 위해 사용
  // 두 번째 : 정상적인 데이터를 전달할 때 사용.

var echo = function(params, callback){
  console.log("JSON-RPC의 echo가 호출되었습니다.");
  console.dir(params);
  callback(null, params);
};

module.exports = echo;
