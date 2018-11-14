package _07_Socket;

import java.io.IOException;
import java.net.ServerSocket;

public class LocalPortScanner {
    public static void main(String[] args) {
        for (int port = 1; port <= 65535; port++) {
            try {
                ServerSocket server = new ServerSocket(port);
            } catch (IOException e) {
                // 해당 포트로 소켓을 연결하지 못했을 경우에는 여기로 온다.
                System.err.println("There is a server on port : " + port + ".");
            }
        }
    }
}