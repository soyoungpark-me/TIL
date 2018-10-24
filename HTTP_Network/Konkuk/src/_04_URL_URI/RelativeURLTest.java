package _04_URL_URI;

import java.net.MalformedURLException;
import java.net.URL;

public class RelativeURLTest {
    public static void main(String[] args) {
        try {
            URL u1 = new URL("http://www.ibiblio.org/javafaq/index.html");
            URL u2 = new URL(u1, "list.html");
            System.out.println(u1); // 결과 : http://www.ibiblio.org/javafaq/index.html
            System.out.println(u2); // 결과 : http://www.ibiblio.org/javafaq/list.html (상대 주소로 대체되었다)
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }
}
