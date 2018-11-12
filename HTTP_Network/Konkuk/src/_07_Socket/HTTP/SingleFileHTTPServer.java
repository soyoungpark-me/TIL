package _07_Socket.HTTP;

import java.io.*;
import java.net.*;
import java.nio.charset.Charset;
import java.nio.file.*;
import java.util.concurrent.*;
import java.util.logging.*;

public class SingleFileHTTPServer {
    private static final Logger logger = Logger.getLogger("SingleFileHTTPServer");

    private final byte[] content;
    private final byte[] header;
    private final int port;
    private final String encoding;

    public SingleFileHTTPServer(String data, String encoding, String mimeType, int port) throws UnsupportedEncodingException {
        this(data.getBytes(encoding), encoding, mimeType, port);
    }

    // 생성자에서 header와 port, encoding과 같은 기본 정보들을 먼저 세팅해준다.
    public SingleFileHTTPServer(byte[] data, String encoding, String mimeType, int port) {
        this.content = data;
        this.port = port;
        this.encoding = encoding;
        String header = "HTTP/1.0 200 OK\r\n"
            + "Server: OneFile 2.0\r\n"
            + "Content-length: " + this.content.length + "\r\n"
            + "Content-type: " + mimeType + "; charset=" + encoding + "\r\n\r\n";
        this.header = header.getBytes(Charset.forName("US-ASCII"));
    }

    // 스레드 풀을 이용해서 스레드를 몇개 (여기서는 100개) 만든 후, 소켓을 생성해 커넥션을 연다.
    // 커넥션은 생성자에서 주어진 포트를 이용한다.
    public void start() {
        ExecutorService pool = Executors.newFixedThreadPool(100);
        try (ServerSocket server = new ServerSocket(this.port)) {
            // 주어진 포트로 소켓을 생성한다.
            logger.info("Accepting connections on port " + server.getLocalPort());
            logger.info("Data to be sent:");
            logger.info(new String(this.content, encoding));

            while (true) {
                try {
                    // 클라이언트로부터 요청이 들어오면 accept()를 통해 소켓을 연결한다.
                    // 그리고 쓰레드 풀으로 새 HTTPHandler 객체를 넣어준다.
                    Socket connection = server.accept();
                    pool.submit(new HTTPHandler(connection));
                } catch (IOException ex) {
                    logger.log(Level.WARNING, "Exception accepting connection", ex);
                } catch (RuntimeException ex) {
                    logger.log(Level.SEVERE, "Unexpected error", ex);
                }
            }
        } catch (IOException ex) {
            logger.log(Level.SEVERE, "Could not start server", ex);
        }
    }

    // HTTPHandler 객체는 SingleFileHTTPServer의 쓰레드 풀에 들어가는 Callable 객체이다.
    // 여기서는 SingleFileHTTPServer에서 연결된 소켓 객체를 생성자로 받아들인 뒤에 요청을 처리한다.
    private class HTTPHandler implements Callable<Void> {
        private final Socket connection;

        // 생성자에서 소켓 객체 (커넥션)을 받아들여서 가지고 있는다.
        HTTPHandler(Socket connection) {
        this.connection = connection;
        }

        @Override
        public Void call() throws IOException {
           try {
               // 실제로 서버에서 처리하는 부분은 이 부분이 된다.
               // 먼저 소켓 커넥션의 Input, Output 스트림을 가져온다.
               OutputStream out = new BufferedOutputStream(connection.getOutputStream());
               InputStream in = new BufferedInputStream(connection.getInputStream());
               // read the first line only; that's all we need

               StringBuilder request = new StringBuilder(80);

               // inputStream을 통해 들어온 내용을 읽어들인 뒤에 request 스트링에 붙여준다.
               while (true) {
                   int c = in.read();
                   if (c == '\r' || c == '\n' || c == -1) break;
                   request.append((char) c);
               }

               // If this is HTTP/1.0 or later send a MIME header
               if (request.toString().indexOf("HTTP/") != -1) {
                   out.write(header);
               }
               out.write(content);
               out.flush();
           } catch (IOException ex) {
               logger.log(Level.WARNING, "Error writing to client", ex);
           } finally {
               connection.close();
           }
           return null;
        }
    }

    public static void main(String[] args) {
        // 파일이름, 포트번호, 인코딩 순으로 arg를 전달한다.
        // 포트 번호와 인코딩은 주지 않을 경우 80번, UTF-8으로 자동으로 세팅된다.

        int port;
        try {
            port = Integer.parseInt(args[1]);
            if (port < 1 || port > 65535) port = 80;
        } catch (RuntimeException ex) {
            port = 80;
        }

        String encoding = "UTF-8";
        if (args.length > 2) encoding = args[2];

        try {
            // 인자로 파일의 위치를 가져온 뒤, 해당 파일의 모든 바이트들을 먼저 읽어들인다.
            Path path = Paths.get(args[0]);
            byte[] data = Files.readAllBytes(path);

            // 파일의 content-type을 먼저 가져온 다음에, 생성된 모든 정보로 SingleFileHTTPServer를 시작한다.
            String contentType = URLConnection.getFileNameMap().getContentTypeFor(args[0]);
            SingleFileHTTPServer server = new SingleFileHTTPServer(data, encoding, contentType, port);
            server.start();

        } catch (ArrayIndexOutOfBoundsException ex) {
            System.out.println("Usage: java SingleFileHTTPServer filename port encoding");
        } catch (IOException ex) {
            logger.severe(ex.getMessage());
        }
    }
}