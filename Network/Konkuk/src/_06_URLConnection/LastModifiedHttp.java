package _06_URLConnection;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Date;

public class LastModifiedHttp {
    public static void main(String[] args) {
//        for (int i=0; i < args.length; i++) {
            try {
                URL u = new URL("http://www.ibiblio.org");
                HttpURLConnection http = (HttpURLConnection) u.openConnection();
                http.setRequestMethod("HEAD");
                // 실제로 패킷을 캡쳐해보면 HEAD 메소드로 요청이 보내진다.

                System.out.println(u + " was last modified at " + new Date(http.getLastModified()));
            } catch (ProtocolException e) {
                e.printStackTrace();
            } catch (MalformedURLException e) {
//                System.err.println(args[i] + " is not a URL i understand");
            } catch (IOException e) {
                e.printStackTrace();
            }
//        }
    }
}
