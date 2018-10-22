package _08_URLConnection;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class SourceViewer {
    public static void main(String[] args) {
        try {
            URL u = new URL(args[0]);
            URLConnection uc = u.openConnection();

            // URLConnection 클래스에 있는 getInputStream 메소드를 이용해 InputStream 객체를 가져온다.
            try (InputStream raw = uc.getInputStream()) {
                InputStream buffer = new BufferedInputStream(raw); // BufferedInputStream으로 감싼다.

                Reader reader = new InputStreamReader(buffer); // BufferedInputStream으로 감싼 걸 Reader로 감싼다.
                /* Reader를 이용해 한 글자씩 읽어들인다.
                int c;
                while((c = reader.read()) != -1) {
                    System.out.print((char) c);
                }
                */

                // BufferedReader를 이용해 한 줄씩 읽어들인다.
                // 현재 구조 : InputStream > BufferedInputStream, InputStreamReader > BufferedReader
                BufferedReader in = new BufferedReader(reader); // Reader를 BufferedReader로 감싼다.
                String line;
                while((line = in.readLine()) != null) {
                    System.out.println(line);
                }
            }
        } catch (MalformedURLException e) {
            System.out.println(args[0] + "is not a parseable URL");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
