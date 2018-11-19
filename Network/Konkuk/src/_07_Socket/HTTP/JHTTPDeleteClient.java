package _07_Socket.HTTP;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Map;

public class JHTTPDeleteClient {
    public static void main(String[] args) {
        try {
            String baseURL = "http://localhost:2001/";
            String file = args.length > 0 ? args[0] : "faker.png";

            URL u = new URL(baseURL + file);

            HttpURLConnection conn = (HttpURLConnection) u.openConnection();
            conn.setRequestMethod("DELETE"); // 요청을 세팅한 다음에 연결!
            conn.connect();

            // 헤더 내용 출력하기
            for (Map.Entry<String, List<String>> header : conn.getHeaderFields().entrySet()) {
                for (String value : header.getValue()) {
                    System.out.println(header.getKey() + " : " + value);
                }
            }
            System.out.println();

            // 응답 내용(BODY) 출력하기
            try (InputStream in = conn.getInputStream();
                 ByteArrayOutputStream out = new ByteArrayOutputStream()) {

                byte[] buf = new byte[1024 * 8];
                int length = 0;
                while ((length = in.read(buf)) != -1) {
                    out.write(buf, 0, length);
                }
                System.out.println(new String(out.toByteArray(), "UTF-8"));
            }

            // 접속 해제
            conn.disconnect();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
