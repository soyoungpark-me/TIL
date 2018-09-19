package _04_Object_Stream;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

public class WIndowObjectInputStreamTest {
    public static void main(String[] args) {
        FileInputStream fin = null;
        ObjectInputStream ois = null;

        try {
            fin = new FileInputStream("hwin.dat");
            ois = new ObjectInputStream(fin);

            HelloWindow hwin = (HelloWindow) ois.readObject();
            hwin.setVisible(true);
        } catch (Exception ex) {
            try {
                ois.close();
                fin.close();
            } catch (IOException ioe) {
            }
        }
    }
}

/*
 * 결과는 동일하지만, X버튼을 클릭해도 윈도우가 종료되지 않는다.
 * WindowEventHandler 객체가 java.io.Serializable 인터페이스를 구현하지 않았기 떄문!
 * 따라서 마샬링되지 않는다...!
 */