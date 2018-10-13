package _06_URL_URI;

import java.net.URI;
import java.net.URISyntaxException;

public class URIConstructorTest {
    public static void main(String[] args) {
        // URI class does not depend on underlying protocols
        // URI 클래스는 기본 프로토콜에 종속되지 않음
        try {
            URI voice = new URI("callme+82-10-8903-9859");  // opaque
            URI book = new URI("urn:isbn:1-565-92870-9");   // opaque
            URI web = new URI("https://naver.com");         // hierarchical
//            URI exception = new URI(":");
            // :을 포함하고 있으면 scheme을 포함해야 한다. 없으면 exception

            System.out.println(voice);
            System.out.println(book);
            System.out.println(web);
//            System.out.println(exception);
        } catch (URISyntaxException ex) {
            System.out.println(ex);
        }
    }
}
