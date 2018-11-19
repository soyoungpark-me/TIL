//package javabook.ch12;

/**
 * 클라이언트와 서버 간의 통신에 사용하는 JSON 규격의 메시지를 좀 더 쉽게 사용하려고
 * 자바 객체로 변환하는 데 필요한 클래스
 * 구글에서 만든 JSON 파서인 Gson을 사용
 * {“id” : “user1”, “passwd” : “1234”, “msg” : “hello”, “type” : “msg”}
 */
public class Message {
	private String id;          // 사용자 ID
	private String passwd;      // 사용자 비밀번호
	private String msg;         // 전달 메시지 내용
	private String type;        // 메시지 유형 (login, logout, msg)
	
	public Message() {}
	
	public Message(String id, String passwd, String msg, String type) {
		this.id = id;
		this.passwd = passwd;

		if (msg != "") {
            this.msg = msg;
        } else {
		    if (type.equals("login")) {
		        this.msg = "님이 로그인 했습니다.";
            } else if (type.equals("logout")) {
		        this.msg = "님이 로그아웃 했습니다.";
            }
        }
		this.type = type;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPasswd() {
		return passwd;
	}
	public void setPassword(String passwd) {
		this.passwd = passwd;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
}
