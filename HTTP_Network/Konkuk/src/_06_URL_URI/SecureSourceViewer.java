package _06_URL_URI;

import java.io.*;
import java.net.*;

public class SecureSourceViewer {

	public static void main (String args[]) throws MalformedURLException, UnknownHostException {

//		DialogAuthenticator da = new DialogAuthenticator();

		Authenticator.setDefault(new DialogAuthenticator());
//		Authenticator.setDefault(da);
//		da.show();
		InetAddress addr = InetAddress.getLocalHost();
		int port = 3000;
		String protocol = "http";
		String prompt = null;
		String scheme= "http";
		PasswordAuthentication authentication =
				Authenticator.requestPasswordAuthentication(addr, port, protocol, prompt, scheme);
		// 가져온 인증 정보로 실제로 서버에 인증 요청을 해야하는 것 같다
		System.out.println("username : " + authentication.getUserName());
		System.out.println("password : " + authentication.getPassword());

//		for (int i = 0; i < 1; i++) {
//			try {
//				// Open the URL for reading
////				URL u = new URL(args[i]);
//				URL u = new URL("file:///home/soyoung/desktop/home.html");
//				try (InputStream in = new BufferedInputStream(u.openStream())) {
//					// chain the InputStream to a Reader
//					Reader r = new InputStreamReader(in);
//					int c;
//					while ((c = r.read()) != -1) {
//						System.out.print((char) c);
//					}
//				}
//			} catch (MalformedURLException ex) {
//				System.err.println(args[0] + " is not a parseable URL");
//			} catch (IOException ex) {
//				System.err.println(ex);
//			}
//
//			// print a blank line to separate pages
//			System.out.println();
//		}

		// Since we used the AWT, we have to explicitly exit.
		System.exit(0);
	} 
}
// the following webpage is password-protected (mtopsoft/123456)
//http://www.mtopsoft.com/htmllock/example5.htm
//http://home.konkuk.ac.kr/~leehw/teaching/2016f_AppPro/apppro/example.html