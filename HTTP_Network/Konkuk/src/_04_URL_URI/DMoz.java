package _04_URL_URI;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

public class DMoz {
    public static void main(String[] args) {
        String target = "";
        for (int i=0; i<args.length; i++) {
            target += args[i] + " ";
        }
        target = target.trim();

        QueryString query = new QueryString();
        query.add("q", target);
        // &q=target을 인코딩한 스트링이 만들어진다.

        try {
            URL u = new URL("https://search.yahoo.com/search?p=java&fr=yfp-t&fp=1&toggle=1&cop=mss&ei=UTF-8");
            try (InputStream in = new BufferedInputStream(u.openStream())) {
                InputStreamReader theHTML = new InputStreamReader(in);

                int c;
                while((c = theHTML.read()) != -1) {
                    System.out.print((char) c);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }
}
