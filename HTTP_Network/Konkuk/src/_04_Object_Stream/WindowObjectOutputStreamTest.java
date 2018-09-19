package _04_Object_Stream;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class WindowObjectOutputStreamTest {
    public static void main(String[] args) {
        FileOutputStream fout = null;
        ObjectOutputStream oos = null;

        HelloWindow hwin = new HelloWindow();

        try {
            fout = new FileOutputStream("hwin.dat");
            oos = new ObjectOutputStream(fout);

            oos.writeObject(hwin);
            oos.reset();

            System.out.println("저장되었습니다.");
            hwin.setVisible(true);
        } catch (Exception ex) {
        } finally {
            try {
                oos.close();
                fout.close();
            } catch (IOException ioe) {}
        }
    }
}
