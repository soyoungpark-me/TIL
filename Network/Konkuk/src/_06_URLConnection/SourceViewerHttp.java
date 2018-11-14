package _06_URLConnection;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class SourceViewerHttp {
    public static void main(String[] args) {
        try {
            URL u = new URL("http://www.ibiblio.org");
            HttpURLConnection uc = (HttpURLConnection) u.openConnection();
            int code = uc.getResponseCode();
            String response = uc.getResponseMessage();

            System.out.println("HTTP/1.x " + code + " " + response);
            System.out.println(uc.getHeaderField(0)); // 위, 아래의 결과는 같다.

            for (int i=0;;i++) {
                String header = uc.getHeaderField(i);
                String key = uc.getHeaderFieldKey(i);

                if (header == null || key == null) break;
                System.out.println(uc.getHeaderFieldKey(i) + ": " + header);
            }

            System.out.println();

            try (InputStream in = new BufferedInputStream(uc.getInputStream())) {
                Reader r = new InputStreamReader(in);
                int c;
                while ((c = r.read()) != -1) {
                    System.out.println((char) c);
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (MalformedURLException e) {
            System.err.println("not a parsable URL");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
