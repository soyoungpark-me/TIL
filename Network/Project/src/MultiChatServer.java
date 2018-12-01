import java.io.*;
import java.net.*;
import java.util.*;
import com.google.gson.*;
import java.util.logging.*;

/**
 * 여러 클라이언트와 동시에 연결할 수 있는 서버 구현
 * 연결된 클라이언트 또한 ArrayList를 이용하여 관리
 */
public class MultiChatServer {
	private final Integer PORT = 8888;

	// 서버 소켓 및 클라이언트 연결 소켓
	private ServerSocket ss = null;
	private Socket s = null;

	// 연결된 클라이언트 스레드를 관리하기 위한 ArrayList
	ArrayList<ChatThread> chatThreads = new ArrayList<ChatThread>();

    // 로거 객체
    Logger logger;

	/**
	 * 멀티챗 메인 프로그램
	 * 서버의 메인 실행 메소드; ServerSocket을 생성하고 클라이언트 연결 및 스레드 생성/처리
	 */
	public void start() {
        logger = Logger.getLogger(this.getClass().getName());

		try {
			// 서버 소켓 생성: port 8888
			ss = new ServerSocket(PORT);

			// info 레벨 로깅: "MultiChatServer start"
			logger.info("[MultiChatServer] start server successfully");

			// 무한 루프를 돌면서 클라이언트 연결을 기다림
			while(true) {
				try {
					// 클라이언트 연결을 기다리다 연결이 들어오면 소켓을 저장
					s = ss.accept();
					// 연결된 클라이언트에 대한 ChatThread 생성
					Thread t = new ChatThread();
					// 클라이언트 스레드 리스트에 추가
					chatThreads.add((ChatThread) t);
					// 스레드 시작
					t.start();
				} catch (IOException e) {}
			}
		} catch (Exception e) {
			logger.info("[MultiChatServer] start() Exception 발생!!");
            e.printStackTrace();
		}
	}

	/**
	 * 연결된 모든 클라이언트에 메시지 중계
	 * 서버가 수신한 메시지를 연결된 모든 클라이언트에 전송하는 메소드
	 * @param msg
	 */
	void msgSendAll(String msg) {
		chatThreads.forEach((thread) -> thread.outMsg.println(msg));
	}

	/**
	 * 각각의 클라이언트 관리를 위한 쓰레드 클래스
	 * 각 클라이언트와 연결 유지, 메시지 송수신 담당 스레드 클래스
	 */
	class ChatThread extends Thread {
		// 수신 메시지 및 파싱 메시지 처리를 위한 변수 선언
		String msg;

        // 메시지 객체 생성
		Message m;

        // Json Parser 초기화
		Gson gson = new Gson();
		//Sample Message {"id":"user1","passwd":"1234","msg":"hahaha","type":"msg"};

		// 입출력 스트림
		private BufferedReader inMsg = null;
		private PrintWriter outMsg = null;

		public void run() {
			boolean status = true;
			logger.info("ChatThread start...");

			try {
				// 입출력 스트림 생성
				inMsg = new BufferedReader(new InputStreamReader(s.getInputStream()));
				outMsg = new PrintWriter(s.getOutputStream(),true);

				// 상태정보가 true 이면 루프를 돌면서 사용자로 부터 수신된 메시지 처리
				while(status) {
					// 수신된 메시지를 msg 변수에 저장
					msg = inMsg.readLine();

					// JSON 메시지를 Message 객체로 매핑
					m = gson.fromJson(msg, Message.class);

					// 파싱된 문자열 배열의 두번째 요소 값에 따라 처리
					// 로그아웃 메시지 인 경우
					if(m.getType().equals("logout")) {
						//이 스레드 charThreads에서 제거 및 채팅창에 로그아웃 메세지 전송
						chatThreads.remove(this);
						msgSendAll(msg);

						// 해당 클라이언트 스레드 종료로 인해 status를 업데이트
						status = false;
					}
					// 로그인 메시지 인 경우
					else if(m.getType().equals("login")) {
						//채팅창에 로그인 메세지 전송
						msgSendAll(msg);
					}
					// 그밖의 경우 즉 일반 메시지인 경우
					else {
						msgSendAll(msg);
					}
				}
				// 루프를 벗어나면 클라이언트 연결 종료 이므로 스레드 인터럽트 및 info 레벨 로깅
				this.interrupt();
				logger.info(this.getName() + " 종료됨!!");
			} catch (IOException e) {
				chatThreads.remove(this); //chatThread에서 현재 스레드 제거
				logger.info("[ChatThread] run() IOException 발생!!");
                e.printStackTrace();
			}
		}
	}

    public static void main(String[] args){
    	//MultiChatServer 객체 생성 및 시작
		MultiChatServer server = new MultiChatServer();
		server.start();
    }
}