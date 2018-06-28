console.log("handler_info 파일이 로딩되었습니다.");

var handler_info = [
  // file : 핸들러 모듈 파일의 이름
  // method : 등록한 핸들러의 이름
  // 이 핸들러의 이름으로 클라이언트가 호출하므로
  // 서버에 핸들러를 등록할 때 어떤 핸들러가 있는지 클라이언트에게도 알려줘야 함.
  {file : './echo', method : 'echo'},
  {file : './echo_error', method : 'echo_error'},
  {file : './echo_encrypted', method : 'echo_encrypted'},
  {file : './add', method : 'add'},
  {file : './list_user', method : 'list_user'},
];

module.exports = handler_info;
