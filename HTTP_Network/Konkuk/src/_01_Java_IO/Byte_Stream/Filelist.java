package _01_Java_IO.Byte_Stream;

import java.io.File;

public class Filelist {
    public static void main(String[] args) {
        File dir = new File("/home/soyoung/document/Study/HTTP_Network/Konkuk/src/test");

        File[] list = dir.listFiles();

        for (int i=0; i<list.length; i++) {
            System.out.println("File " + i + ") " + list[i].getName());
        }
    }
}
