package _04_URL_URI;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import static java.net.URLEncoder.encode;

// URLEncoder 클래스를 사용해 자바 객체 내에서 연속적인 이름과 값의 쌍을 인코딩한다.
// 서버 측의 프로그램에게 데이터를 전달할 때 사용된다.
// URLEncoder.encode() 메소드가 맹목적으로 인코딩한다는 문제를 해결한다.

class QueryString {
    private StringBuilder query = new StringBuilder();

    public QueryString() {}

    public synchronized void add (String name, String value) {
        query.append('&');  // 쿼리를 의미하는 기호를 먼저 붙여준다.
        encode(name, value);
    }

    private synchronized void encode(String name, String value) {
        try {
            query.append(URLEncoder.encode(name, "UTF-8"));
            query.append('=');
            query.append(URLEncoder.encode(value, "UTF-8"));
            //name=value 형태로 뒤에 갖다붙인다.
        } catch (UnsupportedEncodingException ex) {
            throw new RuntimeException("Broken VM does not support UTF-8");
        }
    }

    public synchronized String getQuery() {
        return query.toString();
    }

    @Override
    public String toString() {
        return getQuery();
    }
}

public class QueryStringUsage {
    public static void main(String[] args) {
        QueryString qs = new QueryString();

        qs.add("hi", "en");
        qs.add("as", "Java");
        qs.add("as_epq", "I/O");
        String url = "https://naver.com";
        System.out.println(url);
    }
}
