package _07_Socket;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;

public class RandomPort {
    public static void main(String[] args) {
        try {
            // 생성자의 첫 번째 인자로 0번을 주면 OS가 자동으로 바인딩한다.
            // ServerSocket server = new ServerSocket(0);
            // 이렇게만 쓰면 /0.0.0.0에 바인딩된다.

            ServerSocket server = new ServerSocket((0), 10, InetAddress.getByName("172.16.35.136"));
            // 이렇게 사용 가능한 IP 주소 중 하나를 적어주면 그 주소로 바인딩해준다.

            System.out.println("This server runs on port : " + server.getLocalPort());
            System.out.println("this server runs on hort : " + server.getInetAddress());
        } catch (IOException e) {
            System.err.println(e);
        }
    }
}
