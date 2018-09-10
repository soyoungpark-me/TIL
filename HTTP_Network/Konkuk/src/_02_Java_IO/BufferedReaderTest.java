package _02_Java_IO;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

// 사용자로부터 입력받아 콘솔에 출력하기

public class BufferedReaderTest {
    public static void main(String[] args) throws IOException{
        InputStreamReader isr = new InputStreamReader(System.in);
        BufferedReader br = new BufferedReader(isr);
        String line = "";

        while (!line.equals("End")) {
            line = br.readLine();
            System.out.println("키보드로부터 입력받은 문자열 : " + line + "\n");
        }
    }
}
