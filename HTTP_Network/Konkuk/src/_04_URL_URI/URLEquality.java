package _04_URL_URI;

import java.net.MalformedURLException;
import java.net.URL;

public class URLEquality {
    public static void main(String[] args) {
        try {
            URL www = new URL("http://www.ibiblio.org/");
            URL ibiblio = new URL("http://ibiblio.org/");

            /* same file 출력 */
            // 왜
            if (ibiblio.equals(www)) {
                System.out.println("equals() : " + ibiblio + " is the same as " + www);
            } else {
                System.out.println("equals() : " + ibiblio + " is not the same as " + www);
            }

            /* same file 출력 */
            if (ibiblio.sameFile(www)) {
                System.out.println("sameFile() : " + ibiblio + " is the same as " + www);
            } else {
                System.out.println("sameFile() : " + ibiblio + " is not the same as " + www);
            }

            // equals는 fragment가 있는지 아닌지로 갈린다.
            // hostname 뿐 아니라 뒤에 있는 부분까지 확인한다.
            // 같은 페이지에 있더라도 다른 결과가 나올 수 있음!
            // sameFile은 fragment identifier를 고려하지 않는다.

            URL www1 = new URL ("http://www.ncsa.uiuc.edu/HTMLPrimer.html#GS");
            URL ibiblio1 = new URL("http://www.ncsa.uiuc.edu/HTMLPrimer.html#HD");

            /* not the same file 출력 */
            if (ibiblio1.equals(www1)) {
                System.out.println("equals() : " + ibiblio1 + " is the same as " + www1);
            } else {
                System.out.println("equals() : " + ibiblio1 + " is not the same as " + www1);
            }
            /* same file 출력 */
            if (ibiblio1.sameFile(www1)) {
                System.out.println("sameFile() : " + ibiblio1 + " is the same as " + www1);
            } else {
                System.out.println("sameFile() : " + ibiblio1 + " is not the same as " + www1);
            }
        } catch (MalformedURLException ex) {
            System.out.println(ex);
        }

    }
}
