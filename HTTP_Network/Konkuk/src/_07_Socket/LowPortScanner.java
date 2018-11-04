package _07_Socket;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class LowPortScanner {
    public static void main(String[] args) {
        String host = args.length > 0 ? args[0] : "localhost";

        for (int i=1; i<1024; i++) {
            try {
                Socket s = new Socket(host, i);
                System.out.println("There is a server on port " + i + " of " + host);
                s.close();
            } catch (UnknownHostException e) {
                // host부터 알 수 없을 경우에는 여기에 들어오고,
                break;
            } catch (IOException e) {
                // host는 인식 가능하지만
                // 해당 포트에 소켓 연결이 가능한 서버가 돌고 있지 않을 땐 여기에 들어온다.
            }
        }
    }
}
