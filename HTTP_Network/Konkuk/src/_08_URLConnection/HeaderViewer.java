package _08_URLConnection;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Date;

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
