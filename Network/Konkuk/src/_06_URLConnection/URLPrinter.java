package _06_URLConnection;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;

public class URLPrinter {
    public static void main(String[] args) {
        try {
            URL u = new URL("http://www.lolcats.com/");
            URLConnection uc = u.openConnection();

            if (!uc.getDoInput()) {     // doInput이 false로 설정되어 있으면 true로,
                uc.setDoInput(true);
            }
            if (!uc.getDoOutput()) {    // doOutput이 false로 설정되어 있으면 true로 바꾼다.
                uc.setDoOutput(true);
            }

            uc.setAllowUserInteraction(true);   // userInteraction(인증)이 가능하도록 한다.

            // connectTimeout을 30s로, readTimeout을 45s로 바꾼다.
            // 만약 이 timeout 값을 아주 작게 만들면 다음 Exception이 발생한다.
            // java.net.SocketTimeoutException: connect timed out
            System.out.println("Connect timeout : " + uc.getConnectTimeout());
            System.out.println("Read timeout : " + uc.getReadTimeout());
            uc.setConnectTimeout(30000);
            uc.setReadTimeout(45000);
            System.out.println("Connect timeout : " + uc.getConnectTimeout());
            System.out.println("Read timeout :" + uc.getReadTimeout());

            // usrIntteraction의 디폴트 값이 false이면 true로 바꿔준다.
            if (!URLConnection.getDefaultAllowUserInteraction()) {
                URLConnection.setDefaultAllowUserInteraction(true);
            }

            // useCaches의 현재 값과 디폴트 값을 false로 바꿔준다.
            uc.setUseCaches(false);
            if (uc.getDefaultUseCaches()) {
                uc.setDefaultUseCaches(false);
            }

            // 내 마음대로 헤더 필드를 추가할 수 있다. 이건 wireshark에서도 확인할 수 있다.
            // 하지만 서버에서 이를 인식하지 못할 경우 의미가 없다.
            // 만약 필드 이름에 공백이 들어갈 경우에는 request의 헤더 필드들이 아예 설정되지 않는다. (Exception은 발생X)
            // 만약 value 값에 \n이 들어갈 경우에는 다음 Exception이 발생한다.
            // Exception in thread "main" java.lang.IllegalArgumentException: Illegal character(s) in message header value: want to go home
            uc.addRequestProperty("Hello", "want to go home");
            uc.addRequestProperty("Bye", "want to go home");
            Map<String, List<String>> properties = uc.getRequestProperties();
            System.out.println(properties.getClass());
            System.out.println(uc.getRequestProperties());

            // 이 부분에서 실제 connection이 열린다. configuration이 마쳐진 후에 열리는 것!
            // timeout의 유무를 확인하기 위해서는 실제로 connection을 열어줘야 한다.
            InputStream in = uc.getInputStream();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
