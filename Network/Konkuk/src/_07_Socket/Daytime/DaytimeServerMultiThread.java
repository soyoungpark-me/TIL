package _07_Socket.Daytime;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

public class DaytimeServerMultiThread {
    public static int COUNT = 0;

    public static final int PORT = 2000;

    public static void main(String[] args) {
        try (ServerSocket server = new ServerSocket(PORT)) {
            while (true) {
                try {
                    Socket connection = server.accept();
                    System.out.println(++COUNT + ") New Connection is opened");

                    // 커넥션이 생성되면, 실제로 연결된 소켓에서 해 줘야 하는 작업들을 수행하는 쓰레드를 생성한다.
                    // 쓰레드를 생성해 start 시킨 이후에는 다시 while문을 돌면서 새 연결을 생성한다.
                    Thread task = new DaytimeThread(connection);
                    task.start();
                } catch (IOException e) {}
            }
        } catch (IOException e) {
            System.err.println("Couldn't start server");
        }
    }

    private static class DaytimeThread extends Thread {
        private Socket connection;

        // 커넥션을 생성자의 인자로 받아온다.
        DaytimeThread(Socket connection) {
            this.connection = connection;
        }

        @Override
        public void run() {
            try {
                System.out.println(COUNT + ") New Thread has created");

                // 밑의 내용은 DaytimeServer에서 커넥션을 만들 때마다 해줬던 내용과 동일하다.
                Writer out = new OutputStreamWriter(connection.getOutputStream());
                Date now = new Date();
                out.write(now.toString() + "\r\n");
                out.flush();
            } catch (IOException e) {
                System.err.println(e);
            } finally {
                try {
                    connection.close();
                } catch (IOException e) {}
            }
        }
    }
}

/*
기존의, 앞의 요청이 모두 끝나야 다음 요청을 처리할 수 있다는 점을 해결됐지만
무한정 쓰레드를 생성하다 보면 다음 에러가 발생한다.
    java.lang.OutOfMemoryError: unable to create new native thread
연결 요청마다 쓰레드를 만들게 되면, 쓰레드를 만들고 사용한 뒤 없애는 것도 사실 오버헤드가 있기 때문에 비효율적일 수 있다.
또한, DoS(Denial of Service) 공격에 취약해질 수 있는 단점이 있다.

-> 쓰레드 풀을 만들어 해결한다.
    쓰레드 풀을 만들면, 먼저 몇 개의 쓰레드를 먼저 만들어 두고,
    새로운 요청이 오면 만들어 놓은 쓰레드 풀에서 쓰레드를 가져와 쓴 뒤, 다 쓰고 나면 반환한다.
 */