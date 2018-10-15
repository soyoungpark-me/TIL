package _06_URL_URI;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class GontentGetter {
    public static void main(String args[]) {
        if (args.length > 0) {
            try {
                URL u = new URL(args[0]);
                Object o = u.getContent();
                System.out.println("I got a " + o.getClass().getName());
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
