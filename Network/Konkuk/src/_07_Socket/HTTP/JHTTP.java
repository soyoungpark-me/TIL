package _07_Socket.HTTP;

import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

// 클라이언트가 원하는 파일을 리퀘스트하고,
// 서버에 그 파일이 있으면 내용을 보내주고, 없으면 404 NOT FOUND 응답을 반환한다!

public class JHTTP {
    private static final Logger logger = Logger.getLogger(JHTTP.class.getCanonicalName());
    private static final int NUM_THREADS = 50;
    private static final String INDEX_FILE = "index.html";
    // default 파일이 된다. 클라이언트가 파일이 아닌 디렉토리로 접근한다면 (이 파일이 있을 경우에) 보여준다!

    private final File rootDirectory;
    private final int port;

    // 생성자에서 root 디렉토리와 포트 넘버를 받아와 설정해준다.
    public JHTTP(File rootDirectory, int port) throws IOException {
        // 입력 받은 디렉토리가 디렉토리가 아니거나 잘못된 경로일 경우 익셉션을 발생시킨다.
        if (!rootDirectory.isDirectory()) {
            throw new IOException(rootDirectory + " does not exist as a directory");
        }

        this.rootDirectory = rootDirectory;
        this.port = port;
    }

    // 쓰레드 풀과 소켓을 만들고, RequestProcessor 객체를 만든다.
    // 가져온 리퀘스트를 입력 인자로 주어 submit한다.
    public void start() throws IOException {
        ExecutorService pool = Executors.newFixedThreadPool(NUM_THREADS);
        try (ServerSocket server = new ServerSocket(port)) {
            logger.info("Accepting connections on port " + server.getLocalPort());
            logger.info("Document Root: " + rootDirectory);

            while (true) {
                try {
                    Socket request = server.accept();
                    Runnable r = new RequestProcessor(rootDirectory, INDEX_FILE, request);
                    pool.submit(r);
                    // 연결 요청이 오면 해당 요청을 request에 저장하고, 해당 request로 RequestProcessor 객체를 생성한다.
                    // 그리고 미리 생성해 둔 쓰레드 풀으로 보낸다.
                } catch (IOException e) {
                    logger.log(Level.WARNING, "Error accepting connection", e);
                }
            }
        }
    }

    // cmd에서 디렉토리 경로와 포트를 가져와 설정해준다.
    public static void main(String[] args) {
        File docroot;

        try {
            docroot = new File(args[0]);
        } catch (ArrayIndexOutOfBoundsException e) {
            System.err.println("Usage: java JHTTP docroot port"); // 사용법 명시
            return;
        }

        int port;
        try {
            port = Integer.parseInt(args[1]);
            if (port < 0 || port > 65535) port = 80;
        } catch (RuntimeException e) {
            port = 80;
        }

        // 설정이 완료되었을 경우 JHTTP 객체를 생성하고 start해 서버를 구동시킨다!
        try {
            JHTTP webServer = new JHTTP(docroot, port);
            webServer.start();
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Server could not start", e);
        }
    }
}
