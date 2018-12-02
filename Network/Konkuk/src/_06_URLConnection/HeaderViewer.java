package _06_URLConnection;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Date;

/**
 * URLConnection에서 헤더를 읽는 6가지 메소드를 사용해
 * 콘텐츠 타입 / 콘텐츠 길이 / 콘텐츠 인코딩 / 마지막 변경일 / 만료일 / 현재 시간을 출력한다!
 *
 * + 각 타입이 설정되지 않았을 경우 어떤 타입을 반환하는지 주의하자
 */
public class HeaderViewer {
    public static void main(String[] args) {
        try {
            URL u = new URL("https://soyoungpark.me");
            URLConnection uc = u.openConnection();
            System.out.println("Content-Type : " + uc.getContentType());

            if (uc.getContentEncoding() != null) {
                System.out.println("Content-Encoding : " + uc.getContentEncoding());
            }
            if (uc.getDate() != 0) {
                System.out.println("Date : " + new Date(uc.getDate()));
            }
            if (uc.getLastModified() != 0) {
                System.out.println("Last-Modified : " + uc.getLastModified());
            }
            if (uc.getExpiration() != 0) {
                System.out.println("Expiration date : " + uc.getExpiration());
            }
            if (uc.getContentLength() != -1) {
                System.out.println("Content-Length : " + uc.getContentLength());
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
