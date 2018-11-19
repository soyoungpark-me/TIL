import javax.swing.*;

/**
 * 화면에 필요한 데이터를 제공하고 업데이트하는 기능 제공
 * 채팅 프로그램에서 데이터 변경이 수시로 발생하는 부분
 */
public class MultiChatData {
    // 데이터 제공 객체
    JTextArea msgOut;

    /**
     * 데이터를 변경할 때 업데이트할 UI 컴포넌트를 등록
     * JComponent 객체를 파라미터로 받아 데이터 변화에 대응 처리
     * 현재 구현은 단일 클래스로만 되어 있으나 리스트나 맵 형태로 운영할 수 있음.
     * @param comp
     */
    public void addObj(JComponent comp) {
        this.msgOut = (JTextArea)comp;
    }

    /**
     * 입력인자로 전달된 메시지 내용으로 UI 데이터 업데이트; 채팅 메시지 창의 텍스트를 추가하는 작업 수행
     * 데이터 변화가 발생했을때 UI 에 데이터 변경을 반영하기 위한 메서드
     * @param msg
     */
    public void refreshData(String msg) {
        // JTextArea 에 수신된 메시지 추가
        msgOut.append(msg);
    }
}
