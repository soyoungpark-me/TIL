package _04_URL_URI;

import java.net.URI;
import java.net.URISyntaxException;

// URI의 구성 요소
public class URISplitter {
    public static void main (String args[]) {
        // arg를 가져와서 각 arg마다 URI 객체를 만든다.

        for (int i=0; i<args.length; i++) {
            try {
                URI u = new URI(args[i]);
                System.out.println("The URI is " + u);

                if (u.isOpaque()) {
                    // 훤히 다 보인다. 계층적 구조가 아니라 flat한 형태. slash가 있느냐
                    // scheme (프로토콜) 뒤에 slash가 있는지 없는지로 판단.
                    // 뒤에 slash가 하나라도 붙으면 계층적이라고 판단한다.
                    System.out.println("Thie is an opaque URI"); // opaque :
                    System.out.println("The scheme is " + u.getScheme());
                    System.out.println("The scheme specific part is " + u.getSchemeSpecificPart());
                    System.out.println("The fragment ID is " + u.getFragment());
                } else {
                    // 계층적 URI일 경우 host, userInfo, port, authority, query, fragment, path를 가져올 수 있다.
                    System.out.println("This is a hierarchical URI");
                    System.out.println("The scheme is " + u.getScheme());

                    try {
                        // authority가 제대로 되어 있는 경우인지 확인!
                        u = u.parseServerAuthority();
                        System.out.println("The host is " + u.getHost());
                        System.out.println("The user info is " + u.getUserInfo());
                        System.out.println("The port is " + u.getPort());
                    } catch (URISyntaxException ex) { // 기관 정보를 분석할 수 없는 경우 발생한다.
                        System.out.println("The authority is " + u.getAuthority());
                    }
                    System.out.println("The path is " + u.getPath());
                    System.out.println("The query string is " + u.getQuery());
                    System.out.println("The fragment ID is " + u.getFragment());
                }
            } catch (URISyntaxException e) {
                e.printStackTrace();
                System.out.println(args[i] + " does not seem to be a URI.");
            }
            System.out.println();
        }
    }
}
