package _04_Object_Stream;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.Serializable;

public class HelloWindow extends Frame implements Serializable {
    public HelloWindow() {
        super("HelloWindow");

        setLayout(new BorderLayout());
        add("Center", new Label("Hello Window"));
        addWindowListener(new WindowEventHandler());
        setSize(200, 200);
    }

    class WindowEventHandler extends WindowAdapter {
        public void windowClosing(WindowEvent e) {
            System.out.println("원도우를 종료합니다.");
            System.exit(0);
        }
    }
}
