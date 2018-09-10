package _03_Byte_Stream;

import java.io.FileInputStream;
import java.io.IOException;

public class FileView {
    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("사용법 : java FileView 파일이름");
            System.exit(0);
        }

        FileInputStream fis = null;
        try {
            fis = new FileInputStream(args[0]);
            int i = 0;
            while ((i = fis.read()) != -1) {
                System.out.write(i);
            }
        } catch (Exception ex) {
            System.out.println(ex);
        } finally {
            try {
                fis.close();
            } catch (IOException e) {}
        }
    }
}
