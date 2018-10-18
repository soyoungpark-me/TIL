package _06_URL_URI;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

// 객체 가져오기
public class ContentGetter {
    public static void main(String args[]) {
        if (args.length > 0) {
            try {
                URL u = new URL(args[0]);
                Object o = u.getContent();
                System.out.println("I got a " + o.getClass().getName()); // Object의 변환된 클래스 이름을 얻는다.
                // 어떤 종류의 객체가 반환될지 예측하기 어렵다.
                // instanceof 연산자로 확인해야 한다.
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
