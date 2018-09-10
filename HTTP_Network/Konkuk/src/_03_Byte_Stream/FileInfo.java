package _03_Byte_Stream;

import java.io.File;
import java.io.IOException;

public class FileInfo {
    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("사용법 : java FileInfo 파일이름");
            System.exit(0);
        }
        File file = new File(args[0]);
        if (file.exists()){ // 파일이 존재할 경우
            System.out.println("length : " + file.length());
            System.out.println("canRead : " + file.canRead());
            System.out.println("canWrite : " + file.canWrite());
            System.out.println("getAbsolutePath : " + file.getAbsolutePath());
            try {
                System.out.println("getCanonicalPath : " + file.getCanonicalPath());
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println("getName : " + file.getName());
            System.out.println("getParent : " + file.getParent());
            System.out.println("getPath : " + file.getPath());
        }
    }
}
