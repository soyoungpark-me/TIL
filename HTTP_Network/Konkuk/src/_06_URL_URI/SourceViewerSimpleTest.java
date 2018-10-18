package _06_URL_URI;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

public class SourceViewerSimpleTest {
    public static void main(String[] args) {
        InputStream in = null;

        try {
            URL u = new URL("http://naver.com");
            in = u.openStream();    // openStream은 InputStream 객체를 반환한다.
            int c;
            while ((c = in.read()) != -1) System.out.write(c);  // 끝까지 계속 읽어들인다.
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally { // InputStream 자원을 해제해줘야 한다.
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException e) {}
        }
    }
}
