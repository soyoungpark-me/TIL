import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;

import javax.swing.*;
import javax.swing.border.Border;

/*
 * swing 용어정리
 * - Container : 창의 역할. 다수의 Container 위에 Component가 올라간다.
 *   ex. Frame, Window, Panel, Dialog, Applet ...
 * - Component : 실제로 Container 위에 올라가는 것들.
 *   ex. Button, TextField, TextArea, List ...
 * - LayoutManager : Container 위에 Component들을 올릴 때 레이아웃을 배치하는 방법.
 *   ex. FlowLayout, BorderLayout, GridLayout, CardLayout, GridBackLayout ...
 */

public class MultiChatUI extends JFrame {
    private JFrame frame;               // 컨테이너

    private JPanel loginPanel;          // 로그인 패널
    protected JButton loginButton;      // 로그인 버튼

    private JLabel inLabel;             // 대화명 라벨
    protected JLabel outLabel;          // 대화명 출력 라벨
    protected JTextField idInput;       // 대화명 입력 텍스트필드

    private JPanel logoutPanel;         // 로그아웃 패널
    protected JButton logoutButton;     // 로그아웃 버튼

    private JPanel msgPanel;            // 메시지 입력 패널 구성
    protected JTextField msgInput;      // 메시지 입력 텍스트필드
    protected JButton exitButton;       // 종료 버튼

    private JPanel listPanel;
    protected JTextArea msgOut;         // 채팅 내용 출력창

    protected Container tab;            // 화면 구성 전환을 위한 카드레이아웃
    protected CardLayout cardLayout;

    protected String id;                // 로그인 아이디 저장 필드

    public MultiChatUI() {
        frame = new JFrame("::MULTICHAT::");   // 메인 프레임 구성
        setUIFont(new javax.swing.plaf.FontUIResource("San-Serif", Font.BOLD, 14));

        /* Login Panel start (화면 구성, 레이아웃 설정, 입력필드/버튼 생성, 패널에 위젯 구성) */
        loginPanel = new JPanel();
        inLabel = new JLabel("name");
        inLabel.setPreferredSize(new Dimension(50, 30));

        idInput = new JTextField();
        idInput.setPreferredSize(new Dimension(420, 30));
        loginButton = new JButton("LOGIN");
        loginButton.setPreferredSize(new Dimension(100, 30));

        loginPanel.add(inLabel);
        loginPanel.add(idInput);
        loginPanel.add(loginButton);
        /* Login Panel end */

        /* Logout Panel start (화면 구성, 레이아웃 설정, 패널에 위젯 구성)*/
        logoutPanel = new JPanel();
        outLabel = new JLabel();
        outLabel.setPreferredSize(new Dimension(475, 30));
        logoutButton = new JButton("LOGOUT");
        logoutButton.setPreferredSize(new Dimension(100, 30));

        logoutPanel.add(outLabel);
        logoutPanel.add(logoutButton);
        /* Logout Panel end */

        /* Message Panel start (메시지 입력 패널에 위젯 구성) */
        listPanel = new JPanel();
        listPanel.setSize(600, 315);
        msgOut = new JTextArea("");                             // 메시지 출력 영역 초기화
        msgOut.setEditable(false);                              // 출력전용으로 (수정 불가)
        JScrollPane scrollPane = new JScrollPane(msgOut,        // 스크롤바 구성
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        JScrollBar bar = scrollPane.getVerticalScrollBar();
        bar.setPreferredSize(new Dimension(20, 0));
        scrollPane.setPreferredSize(new Dimension(580, 315));
        listPanel.add(scrollPane);

        msgPanel = new JPanel();
        msgPanel.setSize(600, 40);
        /* Message Panel end */

        /* Input Panel start (메시지 입력 패널 구성) */
        msgInput = new JTextField();
        msgInput.setPreferredSize(new Dimension(475, 30));
        exitButton = new JButton("EXIT");
        exitButton.setPreferredSize(new Dimension(100, 30));

        msgPanel.add(msgInput);
        msgPanel.add(exitButton);
        /* Input Panel end */

        cardLayout = new CardLayout();              // 로그인/로그아웃 패널 선택을 위한 CardLayout 패널
        tab = new Container();
        tab.setPreferredSize(new Dimension(600, 36));
        tab.setLayout(cardLayout);
        tab.add(loginPanel, "login");
        tab.add(logoutPanel, "logout");

        frame.add(listPanel, BorderLayout.CENTER);  // 메인 프레임에 패널 배치 (tab, jsp, msgPanel)
        frame.add(msgPanel, BorderLayout.SOUTH);    // 레이아웃 설정
        frame.add(tab, BorderLayout.NORTH);

        cardLayout.show(tab, "login");        // loginPanel 을 우선 보이도록 함.

        /* TODO 추후 컨트롤러가 붙으면 삭제해야 함! */
        exitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                id = idInput.getText();
                outLabel.setText(" 대화명 : " + id);
                cardLayout.show(tab, "logout");
            }
        });

        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                msgOut.setText("");
                cardLayout.show(tab, "login");
            }
        });
        /* TODO 추후 컨트롤러가 붙으면 삭제해야 함! */

        frame.setSize(600, 400);    // 프레임 크기 자동으로 설정
        frame.setResizable(false);              // 프레임 크기 조정 불가 설정
        frame.setVisible(true);                 // 프레임이 보여지도록 함
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    /**
     * 이벤트 리스너 등록을 위한 메서드로 파라미터의 리스너 객체는 컨트롤러에서 구현한 객체가 됨.
     * 따라서 실제 이벤트 처리는 컨트롤러 클래스 코드를 따라감.
     * @param listener
     */
    public void addButtonActionListener(ActionListener listener) {
        // 이벤트 리스너 등록
    }

    /* TODO 추후 컨트롤러가 붙으면 삭제해야 함! */
    public static void main(String[] args) {
        MultiChatUI v = new MultiChatUI();
    	
    	v.addButtonActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 여기 있던 부분이 먹통이어서, 일단 위에 버튼마다 리스너를 추가해뒀습니다!
                // 나중에 컨트롤러가 붙으면 해당 내용도 같이 지우겠습니다.
            }
        });
    }
    /* TODO 추후 컨트롤러가 붙으면 삭제해야 함! */

    private void setUIFont(javax.swing.plaf.FontUIResource f) {
        java.util.Enumeration keys = UIManager.getDefaults().keys();
        while (keys.hasMoreElements()) {
            Object key = keys.nextElement();
            Object value = UIManager.get(key);
            if (value instanceof javax.swing.plaf.FontUIResource)
                UIManager.put(key, f);
        }
    }
}
