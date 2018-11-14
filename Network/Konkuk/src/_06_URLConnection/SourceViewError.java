package _06_URLConnection;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class SourceViewError {
    public static void main(String[] args) {
        try {
//            URL u = new URL("https://github.com/3457soso/non");
            URL u = new URL("http://www.ibiblio.org/notfound");
            HttpURLConnection uc = (HttpURLConnection) u.openConnection();
            try (InputStream raw = uc.getInputStream()) {
                printFromStream(raw);
            } catch (IOException ex) {
                System.out.println("[ page is not found ]");
                printFromStream(uc.getErrorStream()); // 존재하지 않는 경로로 요청이 여기로 들어온다.
            }
        } catch (MalformedURLException e) {
            System.err.println("not a parsable URL");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void printFromStream(InputStream raw) throws IOException {
        try (InputStream buffer = new BufferedInputStream(raw)) {
            Reader reader = new InputStreamReader(buffer);

            int c;
            while ((c = reader.read()) != -1) {
                System.out.print((char) c);
            }
        }
    }
}
