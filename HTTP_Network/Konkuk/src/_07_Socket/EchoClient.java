package _07_Socket;

import java.io.*;
import java.net.*;

public class EchoClient {
    public static void main(String[] args) throws IOException {
        String hostName = "localhost";
        int portNumber = 2000;

        try (Socket echoSocket = new Socket(hostName, portNumber);
             // 클라이언트에서 서버로 데이터를 쓰기 위해 OutputStream을 가져온 뒤 PrintWriter 객체를 생성한다.
             PrintWriter out = new PrintWriter(echoSocket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(echoSocket.getInputStream()));
             BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in))) {
            String userInput;
            while ((userInput = stdIn.readLine()) != null) {
                if (userInput.length() == 0)
                    break;
                out.println(userInput);
                System.out.println("echo: " + in.readLine());
            }
            echoSocket.close();
        }catch (UnknownHostException e) {
            System.err.println("Don't know about host " + hostName);
            System.exit(1);
        }catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to " + hostName);
            System.exit(1);
        }
    }
}
