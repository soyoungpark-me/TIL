package _06_URL_URI;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class EncoderTest {
    public static void main(String[] args) {
        try {
            // URL 주소가 있는데 space, 한글 문자 등을 다 포함하고 있다면
            // 그 URL을 그대로 사용하지 않고 인코딩을 해야한다.
            // 해당 URL에 존재하는 슬래시 같이 꼭 필요한 친구도 함께 인코딩이 되어버린다.
            // -> 그대로 쓸 수는 없다!

            System.out.println(URLEncoder.encode("This string has spaces", "UTF-8"));
            System.out.println(URLEncoder.encode("This*string*has*asterrisks", "UTF-8"));
            System.out.println(URLEncoder.encode("This%string%has%percent%signs", "UTF-8"));
            System.out.println(URLEncoder.encode("This+string+has+pluses", "UTF-8"));
            System.out.println(URLEncoder.encode("This/string/has/slashes", "UTF-8"));
            System.out.println(URLEncoder.encode("This\"string\"has\"spaces", "UTF-8"));
            System.out.println(URLEncoder.encode("This:string:has:colons", "UTF-8"));
            System.out.println(URLEncoder.encode("This~string~has~tildes", "UTF-8"));
            System.out.println(URLEncoder.encode("This(string)has(parentheses)", "UTF-8"));
            System.out.println(URLEncoder.encode("This.string.has.periods", "UTF-8"));
            System.out.println(URLEncoder.encode("This=string=has=equals=signs", "UTF-8"));
            System.out.println(URLEncoder.encode("This&string&has&ampersands", "UTF-8"));
        } catch(UnsupportedEncodingException ex) {
            throw new RuntimeException("Broken VM does not support UTF-8");
        }
    }

}

