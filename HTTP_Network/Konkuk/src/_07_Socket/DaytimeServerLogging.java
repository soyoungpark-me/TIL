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
import java.util.logging.Level;
import java.util.logging.Logger;

public class DaytimeServerLogging {
    public final static int PORT = 2000;
    public final static Logger auditLogger = Logger.getLogger("requests");
    public final static Logger errorLogger = Logger.getLogger("errors");
    // 먼저 두 가지의 Logger를 생성한다. 하나는 요청을 기록하고, 하나는 에러를 기록한다.

    public static void main(String[] args) {
        ExecutorService pool = Executors.newFixedThreadPool(50);

        try (ServerSocket server = new ServerSocket(PORT)) {
            while (true) {
                try {
                    Socket connection = server.accept();
                    Callable<Void> task = new DaytimeTask(connection);
                    pool.submit(task);
                } catch (IOException e) {
                    errorLogger.log(Level.SEVERE, "accept error", e);
                } catch (RuntimeException e) {
                    errorLogger.log(Level.SEVERE, "unexpected error " + e.getMessage(), e);
                }
            }
        } catch (IOException e) {
            errorLogger.log(Level.SEVERE, "Couldn't start server", e);
        } catch (RuntimeException e) {
            errorLogger.log(Level.SEVERE, "Couldn't start server : " + e.getMessage(), e);
            // 11월 12, 2018 11:12:04 오전 _07_Socket.DaytimeServerLogging main
            // 심각: Couldn't start server
            // java.net.BindException: 주소가 이미 사용 중입니다 (Bind failed)
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
                Date now = new Date();
                auditLogger.info(now + " " + connection.getRemoteSocketAddress());
                // 11월 12, 2018 11:09:02 오전 _07_Socket.DaytimeServerLogging$DaytimeTask call
                // 정보: Mon Nov 12 11:09:02 KST 2018 /127.0.0.1:55864
                //      info 레벨으로 logging 했기 떄문에 앞에 [정보]가 붙는다.
                //      getRemoteSocketAddress()를 호출했기 떄문에 클라이언트 호스트와 포트도 같이 출력된다.

                Writer out = new OutputStreamWriter(connection.getOutputStream());
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
