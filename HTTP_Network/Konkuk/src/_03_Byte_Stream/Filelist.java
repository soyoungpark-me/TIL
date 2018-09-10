package _03_Byte_Stream;

import java.io.File;
import java.net.URL;

public class Filelist {
    public static void main(String[] args) {
        File dir = new File("/home/soyoung/document/Study/HTTP_Network/Konkuk/src/test");

        File[] list = dir.listFiles();

        for (int i=0; i<list.length; i++) {
            System.out.println("File " + i + ") " + list[i].getName());
        }
    }
}
