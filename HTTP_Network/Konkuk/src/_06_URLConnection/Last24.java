package _06_URLConnection;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Date;

public class Last24 {
    public static void main(String[] args) {
        Date today = new Date();
        final long MILLI_SECONDS_PER_DAY = 24 * 60 * 60 * 1000; // 1일을 ms 단위로 표현한 값

        try {
            // URL u = new URL("http://www.oreilly.com");
            // URL u = new URL("http://www.konkuk.ac.kr");
            // 결과 : HTTP/1.1 304 Not Modified
            // 건국대 url로 연결할 경우에는 response의 body가 출력되지 않는다. 어제 이후로 수정되지 않았기 떄문!

            URL u = new URL("https://www.oreilly.com");

            URLConnection uc = u.openConnection();

            // IfModifiedSince 값을 읽어와서 Date 형식으로 변환한다.
            System.out.println("Original if modified since : " + new Date(uc.getIfModifiedSince()));

            // 현재 시간을 가져오고 하루를 뺸 다음에, 결과를 IfModifiedSince에 세팅해준다.
            // 결과 : If-Modified-Since: Tue, 23 Oct 2018 01:34:19 GMT
            uc.setIfModifiedSince((new Date(today.getTime() - MILLI_SECONDS_PER_DAY)).getTime());

            // 바뀐 값을 출력해준다. (현재 날짜에서 하루를 뺸 값)
            System.out.println("Will retrieve file if it's modified since : " + new Date(uc.getIfModifiedSince()));

            // Exercise : 아래 URL이 마지막으로 수정된 시점 예측하기
            System.out.println("Last Modified : " + new Date(uc.getLastModified()));

            try (InputStream in = new BufferedInputStream(uc.getInputStream())) {
                /* reader로 하나씩 읽어들이기
                Reader r = new InputStreamReader(in);
                int c;
                while ((c = r.read()) != -1) {
                    System.out.print((char) c);
                }
                System.out.println();
                */

                // InputStream > BufferedInputStream > InputStreamReader > BufferedReader
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    System.out.println(line);
                }
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}