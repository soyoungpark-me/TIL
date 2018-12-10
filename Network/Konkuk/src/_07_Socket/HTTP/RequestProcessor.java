package _07_Socket.HTTP;

import java.io.*;
import java.net.Socket;
import java.net.URLConnection;
import java.nio.file.Files;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * 실제로 클라이언트가 보낸 요청을 처리하고, 응답을 보내주는 일을 담당한다!
 * 기존 SingleFileHTTPServer와 다르게...
 * - 요청이 도착한 메인 스레드에서 직접 각 요청을 처리하지 않고, 들어온 연결을 풀에 저장한다.
 * - 분리된 RequestProcessor 클래스의 인스턴스가 풀로부터 연결을 제거하고 해당 연결을 처리한다!
 */

public class RequestProcessor implements Runnable {
    private final static Logger logger = Logger.getLogger(RequestProcessor.class.getCanonicalName());

    private File rootDirectory;
    private String indexFileName = "index.html"; // default 파일. 추후 바뀔 수 있다.
    private Socket connection;

    public RequestProcessor(File rootDirectory, String indexFileName, Socket connection) {
        if (rootDirectory.isFile()) {
            throw new IllegalArgumentException("rootDirectory must be a directory, not a file");
        }

        try {
            rootDirectory = rootDirectory.getCanonicalFile(); // getCanonicalFile()로 **절대** 경로를 넣어준다.
        } catch (IOException e) {}
        this.rootDirectory = rootDirectory;

        if (indexFileName != null) this.indexFileName = indexFileName;
        this.connection = connection;
    }


    // 존재하는 파일을 쿼리했을 경우 해당 파일을 돌려주고, 아닐 경우에는 404 Not Found를 돌려준다.
    @Override
    public void run() {
        String root = rootDirectory.getPath();
        // raw : 소켓의 outputStream을 BufferedOutputStream으로 감싼다. (파일 쓰기 작업 시 사용)
        // out : raw를 OutputStreamWriter로 감싼다. (헤더 등 문자 쓰기 작업 시 사용)
        try {
            OutputStream raw = new BufferedOutputStream(connection.getOutputStream());
            Writer out = new OutputStreamWriter(raw);
            Reader in = new InputStreamReader(new BufferedInputStream(connection.getInputStream()), "US-ASCII");

            StringBuilder requestLine = new StringBuilder();

            while (true) {
                int c = in.read();
                if (c == '\r' || c == '\n') break; // 헤더 라인만 읽는다.
                requestLine.append((char) c);
            }

            String get = requestLine.toString(); // 맨 첫번째 라인만 가져온다.

            logger.info(connection.getRemoteSocketAddress() + " " + get);

            // 문자열 단위로 스플릿 해준다.
            // 첫 번째 : method    메소드
            // 두 번째 : fileName  파일 이름
            // 세 번째 : version   HTTP 버전
            String[] tokens = get.split("\\s+");
            String method = tokens[0];
            String version = "";
            Boolean fileIsFound = false;

            String fileName = tokens[1];
            if (fileName.endsWith("/")) fileName += indexFileName;
            // 파일 이름을 가져와서, /로 끝나면 뒤에 default 파일 이름을 뒤에 붙여준다.

            String contentType = URLConnection.getFileNameMap().getContentTypeFor(fileName);
            if (tokens.length > 2) {
                version = tokens[2];
            }

            File theFile = new File(rootDirectory, fileName.substring(1, fileName.length()));

            // 파일을 읽을 수 있고, 절대 경로가 가져온 root로 시작한다면 올바른 요청!
            if (theFile.canRead() && theFile.getCanonicalPath().startsWith(root)) {
                fileIsFound = true;
            } else {
                /* 요청한 파일을 읽을 수 없거나, 요청한 절대 경로가 기존의 root 경로와 일치하지 않을 때는 404 Not Found */
                String body = new StringBuilder("<HTML>\r\n")
                        .append("<HEAD><TITLE>File Not Found</TITLE>\r\n")
                        .append("</HEAD>\r\n")
                        .append("<BODY>")
                        .append("<H1>HTTP Error 404: File Not Found</H1>\r\n")
                        .append("</BODY></HTML>\r\n").toString();

                if (version.startsWith("HTTP/")) {
                    sendHeader(out, "HTTP/1.0 404 File Not Found", "text/html; charset=utf-8", body.length());
                }
                out.write(body);
                out.flush();
            }

            /* 요청한 메소드가 GET일 경우 */
            if (method.equals("GET")) {
                // 파일을 읽을 수 있고, 절대 경로가 가져온 root로 시작한다면 올바른 요청!
                if (fileIsFound) {
                    byte[] theData = Files.readAllBytes(theFile.toPath());

                    // HTTP 요청을 받았을 경우에만 HTTP 응답 메시지의 헤더를 전송한다.
                    if (version.startsWith("HTTP/")) {
                        sendHeader(out, "HTTP/1.0 200 OK", contentType, theData.length);
                    }

                    // 해당 파일의 데이터들을 전송하고 flush() 해 준 뒤 마무리!
                    raw.write(theData);
                    raw.flush();
                }
            /* 요청한 메소드가 DELETE일 경우 */
            } else if (method.equals("DELETE")) {
                if (fileIsFound) { // 파일이 존재할 경우에는 삭제를 시도한다.
                    StringBuilder body = new StringBuilder("<HTML>\r\n")
                            .append("<HEAD><TITLE>Delete file</TITLE>\r\n")
                            .append("</HEAD>\r\n")
                            .append("<BODY>");

                    if (theFile.delete()) { // 파일 삭제에 성공했을 경우에는 200
                        body.append("<H1>File is deleted successfully</H1>\r\n");
                    } else {
                        body.append("<H1>File is not deleted</H1>\r\n");
                    }

                    body.append("</BODY></HTML>\r\n");

                    // HTTP 요청을 받았을 경우에만 HTTP 응답 메시지의 헤더를 전송한다.
                    if (version.startsWith("HTTP/")) {
                        sendHeader(out, "HTTP/1.0 200 OK", contentType, body.length());
                    }
                    // TODO 파일 삭제는 되고 응답 헤더는 오는데 body는 오지 않음!
                    // 해당 파일의 데이터들을 전송하고 flush() 해 준 뒤 마무리!
                    out.write(body.toString());
                    out.flush();
                }
            /* 요청한 메소드가 GET과 DELETE가 아닐 경우 501 Not Implemented */
            } else {
                String body = new StringBuilder("<HTML>\r\n")
                        .append("<HEAD><TITLE>Not Implemented</TITLE>\r\n")
                        .append("</HEAD>\r\n")
                        .append("<BODY>")
                        .append("<H1>HTTP Error 501: Not Implemented</H1>\r\n")
                        .append("</BODY></HTML>\r\n").toString();

                if (version.startsWith("HTTP/")) { // send a MIME header
                    sendHeader(out, "HTTP/1.0 501 Not Implemented",
                            "text/html; charset=utf-8", body.length());
                }

                out.write(body);
                out.flush();
            }
        } catch (IOException e) {
            logger.log(Level.WARNING, "Error talking to " + connection.getRemoteSocketAddress(), e);
        } finally {
            // 처리를 완료한 후에는 소켓 연결을 꼭 닫아준다!
            try {
                connection.close();
            } catch (IOException e) {}
        }
    }

    private void sendHeader(Writer out, String responseCode, String contentType, int length) throws IOException {
        Date now = new Date();
        out.write(responseCode + "\r\n");
        out.write("Date: " + now + "\r\n");
        out.write("Server: JHTTP 2.0\r\n");
        out.write("Content-length: " + length + "\r\n");
        out.write("Content-type: " + contentType + "\r\n\r\n");
        out.flush();
    }
}

/*
    [헤더 결과 예시]

    Request Header
    ----------------------------------------
    GET /faker.png HTTP/1.1
    Host: localhost:2001
    Connection: keep-alive
    Cache-Control: max-age=0
    Upgrade-Insecure-Requests: 1
    User-Agent: Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/68.0.3440.106 Safari/537.36
    Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*;q=0.8
    Accept-Encoding: gzip, deflate, br
    Accept-Language: ko-KR,ko;q=0.9,en-US;q=0.8,en;q=0.7

    Response Header
    ----------------------------------------
    HTTP/1.0 200 OK
    Date: Wed Nov 14 11:23:16 KST 2018
    Server: JHTTP 2.0
    Content-length: 276210
    Content-type: image/png
 */