import java.awt.event.*;
import java.io.*;
import java.net.*;
import java.util.logging.*;
import com.google.gson.*;
import static java.util.logging.Level.*;

/**
 * 프로그램의 메인으로, UI와 서버 연결 및 채팅 메시지 전달
 */
public class MultiChatController implements Runnable {
    private final MultiChatUI v;                  // 뷰 클래스 참조 객체
    private final MultiChatData chatData;         // 데이터 클래스 참조 객체

    private final String ip = "203.252.148.148";  // 소켓 연결을 위한 변수 선언
    private final Integer port = 8888;            // TODO ip 및 포트 번호 변경!!!!!
    private Socket socket;
    private BufferedReader inMsg = null;
    private PrintWriter outMsg = null;

    Gson gson = new Gson();                       // 메시지 파싱을 위한 객체 생성
    Message message;

    boolean status;                               // 상태 플래그 (thread while 탈출)
    Logger logger;                                // 로거 객체
    Thread thread;                                // 메시지 수신 스레드

    /**
     * 모델과 뷰 객체를 파라미터로 하는 생성자
     * @param chatData
     * @param v
     */
    public MultiChatController(MultiChatData chatData, MultiChatUI v) {
        // 로거, MultiChatData, MultiChatUI 객체 초기화
        logger = Logger.getLogger("log");
        this.v = v;
        this.chatData = chatData;
    }

    /**
     * 어플리케이션 메인 실행 메서드
     * 컨트롤러 클래스 메인 로직; UI에서 발생한 이벤트를 위임받아 처리
     */
    public void appMain() {
        // 데이터 객체(chatData)에서 (채팅내용 출력창의) 데이터 변화를 처리할 UI 객체 추가
        chatData.addObj(v.msgOut);

        v.addButtonActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Object obj = e.getSource();

                // 종료버튼, 로그인버튼, 로그아웃버튼, 메시지전송버튼(엔터) 처리
                if (obj == v.exitButton) {
                    System.exit(0);
                } else if (obj == v.loginButton) {
                    v.id = v.idInput.getText();
                    v.outLabel.setText(" 대화명 : " + v.id);
                    v.cardLayout.show(v.tab, "logout");

                    connectServer();
                } else if (obj == v.logoutButton) {
                    if (status && outMsg != null) {
                        // 로그아웃 메시지 전송
                        outMsg.println(gson.toJson(new Message(v.id, "", "", "logout")));
                    }
                    // 대화창 클리어
                    v.msgOut.setText("");

                    // 로그인 패널로 전환 및 소켓/스트림 닫기 + status 업데이트
                    v.cardLayout.show(v.tab, "login");

                    try {
                        // TODO outMsg, inMsg 순서를 바꾸면 앱이 뻗는데 이유를 모르겠다...
                        if (outMsg != null) outMsg.close();
                        if (inMsg != null)  inMsg.close();
                        if (socket != null) socket.close();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    } finally {
                        status = false;
                    }
                } else if (obj == v.msgInput) {
                    // 입력된 메시지 전송 (위의 로그아웃 메시지 전송코드와 Message 생성자 참고)
                    String msgContents = v.msgInput.getText();

                    if (status && outMsg != null && msgContents.length() > 0) {
                        outMsg.println(gson.toJson(new Message(v.id, "", msgContents, "msg")));

                        // 입력창 클리어
                        v.msgInput.setText("");
                    }
                }
            }
        });
    }

    /**
     * 서버 접속을 위한 메서드 (멤버필드를 적극 활용하기 바람; 이 메서드 안에서 새롭게 선언이 필요한 변수는 없음)
     */
    public void connectServer() {
        try {
            // 소켓 생성 (ip, port는 임의로 설정하되 나중에 서버에서 듣게될 포트와 동일해야함)
            socket = new Socket(ip, port);

            // INFO 레벨 로깅 (서버 연결에 성공했다는 메시지 화면에 출력)
            logger.info("[MultiChatUI] connect to Server successfully");

            // 입출력(inMsg, outMsg) 스트림 생성
            inMsg = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
            outMsg = new PrintWriter(socket.getOutputStream(), true);

            // 서버에 로그인 메시지 전달
            outMsg.println(gson.toJson(new Message(v.id, "", "", "login")));

            // 메시지 수신을 위한 스레드(thread) 생성 및 스타트
            thread = new Thread(this);
            thread.start();

        } catch(Exception e) {
            logger.log(WARNING, "[MultiChatUI] connectServer() Exception 발생!!");
            e.printStackTrace();
        }
    }

    /**
     * 서버 연결 후 메시지 수신을 UI 동작과 상관없이 독립적으로 처리하는 스레드를 실행
     * 메시지 수신을 독립적으로 처리하기 위한 스레드 실행
     */
    public void run() {
        // 수신 메시지 처리를 위한 변수
        String msgContents;

        //status 업데이트
        this.status = true;

        while (status) {
            try {
                // 메시지 수신 및 파싱
                /**
                 * while ((input = inMsg.readLine()) != null)에서 break 되지 않는 이유
                 *
                 * The BufferedReader will keep on reading the input until it reaches the end
                 * (end of file or stream or source etc).
                 * In this case, the 'end' is the closing of the socket.
                 * So as long as the Socket connection is open, your loop will run,
                 * and the BufferedReader will just wait for more input, looping each time a '\n' is reached.
                 */
                msgContents = inMsg.readLine();

                if (msgContents != null && msgContents != "") {
                    message = gson.fromJson(msgContents, Message.class);

                    // MultiChatData 객체를 통해 데이터 갱신
                    chatData.refreshData(message.getId() + "> " + message.getMsg() + "\n");
                }

                // 커서를 현재 대화 메시지에 보여줌
                v.msgOut.setCaretPosition(v.msgOut.getDocument().getLength());
            } catch(IOException e) {
                logger.log(WARNING,"[MultiChatUI] 메시지 스트림 종료!!");
            }
        }
        logger.info("[MultiChatUI]" + thread.getName()+ " 메시지 수신 스레드 종료됨!!");
    }

    // 프로그램 시작을 위한 메인 메서드
    public static void main(String[] args) {
        //MultiChatController 객체생성 및 appMain() 실행
        MultiChatController multiChatController = new MultiChatController(
            new MultiChatData(), new MultiChatUI()
        );
        multiChatController.appMain();
    }
}