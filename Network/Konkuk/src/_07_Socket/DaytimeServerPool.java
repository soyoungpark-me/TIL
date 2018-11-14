package _07_Socket;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DaytimeServerPool {
    public static int COUNT = 0;
    public final static int PORT = 2000;

    public static void main(String[] args) {
        // 50개의 쓰레드를 미리 만든다.
        ExecutorService pool = Executors.newFixedThreadPool(50);

        try (ServerSocket server = new ServerSocket(PORT)) {
            while (true) {
                try {
                    Socket connection = server.accept();
                    System.out.println(++COUNT + ") New Connection is opened");

                    Callable<Void> task = new DaytimeTask(connection);
                    pool.submit(task);
                } catch (IOException e) {}
            }
        } catch (IOException e) {
            System.err.println("Couldn't start server");
        }
    }

    private static class DaytimeTask implements Callable<Void> {
        private Socket connection;

        // 커넥션을 생성자의 인자로 받아온다.
        DaytimeTask(Socket connection) {
            this.connection = connection;
        }

        @Override
        public Void call() {
            try {
                System.out.println(COUNT + ") New Thread has created");

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

            return null;
        }
    }
}
