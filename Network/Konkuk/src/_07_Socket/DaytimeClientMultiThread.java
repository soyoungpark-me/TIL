package _07_Socket;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;

public class DaytimeClientMultiThread {
    public final static String HOST = "localhost";
    public final static int PORT = 2000;

    public static void main(String[] args) {
        // 테스트를 위해 무한정으로 쓰레드를 생성한다.
        // 결과를 받아오기 전에 새로 쓰레드를 계속 생성하며 연결을 요청한다는 것이 다르다.
        while (true) {
            try {
                Socket connection = new Socket(HOST, PORT);
                Thread task = new DaytimeThread(connection);
                task.start();
            } catch (UnknownHostException e) {
            } catch (IOException e) {}
        }
    }

    private static class DaytimeThread extends Thread {
        private Socket connection;

        DaytimeThread(Socket connection) {
            this.connection = connection;
        }

        @Override
        public void run() {
            try {
                InputStream in = connection.getInputStream();
                StringBuilder time = new StringBuilder();
                InputStreamReader reader = new InputStreamReader(in, "ASCII");

                for (int c = reader.read(); c != -1; c = reader.read()) {
                    time.append((char) c);
                }

                System.out.print(time);
                Thread.sleep(30000); // 30초 동안 대기
            } catch (IOException e) {
            } catch (InterruptedException e) {
            } finally {
                try {
                    connection.close();
                } catch (IOException e) {}
            }
        }
    }
}
