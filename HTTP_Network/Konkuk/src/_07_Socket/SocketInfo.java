package _07_Socket;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class SocketInfo {
    public static void main(String[] args) {
        String host = "www.konkuk.ac.kr";

        try {
            Socket socket = new Socket(host, 80);
            System.out.println("Connected to + " + socket.getInetAddress() + " on port " +
                    socket.getPort() + " from port " + socket.getLocalPort() + " of " +
                    socket.getLocalAddress());

        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
