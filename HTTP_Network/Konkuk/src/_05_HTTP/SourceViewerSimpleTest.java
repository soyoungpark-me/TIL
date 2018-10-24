package _05_HTTP;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

public class SourceViewerSimpleTest {
    public static void main(String args[]) {
        InputStream in = null;
        try {
//            URL u = new URL("http://www.konkuk.ac.kr");
            URL u = new URL("http://www.lolcats.com/images/logo.png");
            in = u.openStream();
            int c;
            while ((c = in.read()) != -1) System.out.write(c);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException e) {}
        }
    }
}
