package _06_URL_URI;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import static java.net.URLEncoder.encode;

class QueryString {
    private StringBuilder query = new StringBuilder();

    public QueryString() {}

    public synchronized void add (String name, String value) {
        query.append('&');
        encode(name, value);
    }

    private synchronized void encode(String name, String value) {
        try {
            query.append(URLEncoder.encode(name, "UTF-8"));
            query.append('=');
            query.append(URLEncoder.encode(value, "UTF-8"));
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
