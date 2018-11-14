package _07_Socket;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;

public class DaytimeClient {
    public static void main(String[] args) {
        String hostname = args.length > 0 ? args[0] : "time.nist.gov";
        // [결과] 58427 18-11-05 02:21:53 00 0 0 565.9 UTC(NIST) *
        // 뒷 부분의 소수 값은 서버의 표준 시각을 그대로 주면, 클라이언트가 받았을 때 더 시간이 흘렀다고 봐야 한다.
        // 이 점을 고려해서 response가 생성되고 클라이언트에 도착할때까지의 시점을 고려해서 보내주는 것.
        // socket의 경우 HTTP 통신과 다르게 path를 지정해주지 않는다.

        Socket socket = null;

        try {
            socket = new Socket(hostname, 2000);
//            socket = new Socket("114.70.22.18", 13);
            socket.setSoTimeout(10000); // 단위는 ms이다. 연결을 시도하고 10초를 넘어가면 exception이 발생한다.
            InputStream in = socket.getInputStream();
            StringBuilder time = new StringBuilder();
            InputStreamReader reader = new InputStreamReader(in, "ASCII");

            for (int c = reader.read(); c != -1; c = reader.read()) {
                time.append((char) c);
            }

            System.out.println(time);
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (socket != null) {
                try {
                    socket.close();
                } catch (IOException e) {}
            }
        }
    }
}
