package _06_URLConnection;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

/**
 * QueryString 클래스를 이용해 데이터를 전송한다.
 */
public class FormPoster {
    private URL url;
    private QueryString query = new QueryString();

    public FormPoster (URL url) {
        // 해당 URL이 http로 시작할 경우에만 url을 세팅해준다.
        if (!url.getProtocol().toLowerCase().startsWith("http")) {
            throw new IllegalArgumentException("Posting only works for http URLs");
        }
        this.url = url;
    }

    // &name=value의 값을 인코딩을 해서 붙여준다.
    public void add(String name, String value) {
        query.add(name, value);
    }

    public URL getUrl() {
        return this.url;
    }

    public InputStream post() throws IOException {
        URLConnection uc = url.openConnection();    // 연결을 연다!

        uc.setDoOutput(true); // 먼저 doOutput을 true로 설정해줘야 한다.
        try (OutputStreamWriter out = new OutputStreamWriter(uc.getOutputStream(), "UTF-8")) {
            /**
             * POST라인, Content-type 헤더, Content-length 헤더는
             * URLConnection에 의해 보내지므로 데이터만 전송하면 된다!
             */
            out.write(query.toString());
            out.write("\r\n"); // 끝을 나타냄 (carry return, line feed)
            out.flush(); // 꼭 flush 해준다! close는 try-catch 문에서 자동으로 해준다.
        }

        return uc.getInputStream();
    }

    public static void main(String[] args) {
        URL url;
        // argument로 url이 주어지면 그걸로 설정하고, 없으면 디폴트 url이 설정된다.
        if (args.length > 0) {
            try {
                url = new URL(args[0]);
            } catch (MalformedURLException e) {
                System.err.println("Usage : java FormPoster url");
                return;
            }
        } else {
            try {
                // 들어온 쿼리 값을 화면에 출력해주는 웹사이트라고 함!
                url = new URL("http://www.cafeaulait.org/books/jnp4/postquery.phtml");
            } catch (MalformedURLException e) {
                System.err.println(e);
                return;
            }
        }

        // 위에서 설정된 url으로 FormPoster 객체를 생성한 다음에 여러 쿼리 값을 붙인다.
        FormPoster poster = new FormPoster(url);
        poster.add("name", "Soyoung Park");
        poster.add("email", "soyoungpark.me@gmail.com");

        // wireshark로 보면 request가 POST로 설정되어 있고, body가 다음과 같이 설정된 것을 확인할 수 있다.
        // HTML Form URL Encoded: application/x-www-form-urlencoded
        // Form item: "&name" = "Soyoung Park"
        //      Key: &name
        //      Value: Soyoung Park
        // Form item: "email" = "soyoungpark.me@gmail.com"

        // query는 escaped 되어서 다음과 같이 저장된다.
        // &name=Soyoung+Park&email=soyoungpark.me%40gmail.com
        
        try (InputStream in = poster.post()) {
            Reader r = new InputStreamReader(in);

            int c;
            while ((c = r.read()) != -1) {
                System.err.print((char) c);
            }
            System.err.println();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


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

