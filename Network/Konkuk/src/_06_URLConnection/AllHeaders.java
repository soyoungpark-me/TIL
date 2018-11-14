package _06_URLConnection;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class AllHeaders {
    public static void main(String[] args) {
        try {
            URL u = new URL("http://www.oreilly.com");
            URLConnection uc = u.openConnection();

            /* 1. Map에 몽땅 담아와서 하나씩 출력하는 방법
            Map<String,List<String>> headers = uc.getHeaderFields();
            for (String key: headers.keySet()) {
                System.out.println(key + " : " + headers.get(key));
            }
            */

            /* 2. 1부터 시작해서 일단 가져온 다음에, null이면 끝내는 방법 */
            // 0번째 헤더같은 경우에는 key 없이 value만 return (HTTP/1.1 301 Moved Permanently)
            int idx = 1; // 0은 null을 가져옴에 주의한다.
            while(true) {
                String key = uc.getHeaderFieldKey(idx);
                if (key == null) break; // null을 가져올 경우에는 더 가져올 게 없으니 break

                System.out.println(key + " : " + uc.getHeaderField(key));
                idx++;
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
