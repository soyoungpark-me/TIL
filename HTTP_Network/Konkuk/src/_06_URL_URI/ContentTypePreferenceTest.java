package _06_URL_URI;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.net.MalformedURLException;
import java.net.URL;

public class ContentTypePreferenceTest {
    public static void main(String[] args) {
        try {
//            URL u = new URL("https://static.invenglobal.com/upload/image/2018/03/18/i1521372528421766.jpeg");
            URL u = new URL("https://naver.com");

            Class<?>[] types = new Class[3];
            types[0] = String.class;
            types[1] = Reader.class;
            types[2] = InputStream.class;
            // 현재 타입에 이미지 관련된 게 없기 때문에 null을 리턴한다.
            Object o = u.getContent(types);

            System.out.println("I got a " + o.getClass().getName());

            // Object가 반환된 결과는 instanceof 연산자로 확인해야 한다.
            if (o instanceof String) {
                System.out.println(o);
            } else if (o instanceof Reader) {
                int c;
                Reader r = (Reader) o;
                while ((c = r.read()) != -1) System.out.print((char) c);
                r.close();
            } else if (o instanceof InputStream) {
                int c;
                InputStream in = (InputStream) o;
                while((c = in.read()) != -1) System.out.write(c);
                in.close();
            } else {
                System.out.println("Error : unexpected Type " + o.getClass());
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
