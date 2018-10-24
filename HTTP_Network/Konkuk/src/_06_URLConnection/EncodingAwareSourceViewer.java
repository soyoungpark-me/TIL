package _06_URLConnection;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class EncodingAwareSourceViewer {
    public static void main(String[] args) {
        try {
            String encoding = "ISO-8859-1"; // default 인코딩 값
            URL u = new URL("https://soyoungpark.me");
            URLConnection uc = u.openConnection();
            String contentType = uc.getContentType(); // ContentType의 value만 String으로 저장한다.
            // ** 실제 connection은 여기서 열림! ** 여기서 req, res를 주고받았어야 실제 헤더를 확인할 수 있다.

            int encodingStart = contentType.indexOf("charset="); // charset=의 index를 가져온다.
            if (encodingStart != -1) { // 이게 -1이라면 charset=이 없는 것임.
                encoding = contentType.substring(encodingStart + 8); // charset=을 자른다.
            }
            System.out.println("contentType : " + contentType);
            System.out.println("encoding : " + encoding);

            InputStream in = new BufferedInputStream(uc.getInputStream());
            Reader r = new InputStreamReader(in, encoding);

            int c;
            while ((c = r.read()) != -1) {
                System.out.print((char) c);
            }
            r.close();
        } catch (UnsupportedEncodingException e) {
            System.err.println("Server sent an encoding Java does not support : " + e.getMessage());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
