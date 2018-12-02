package _06_URLConnection;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * HTTP 서버는 데이터 전송이 끝난 후 항상 명확하게 연결을 종료하진 않는다.
 * - 따라서 클라이언트는 읽기를 언제 멈춰야 하는지 알 수 없다.
 * - 파일을 다운로드하는 서버를 만들 떄는, 파일의 길이를 먼저 확인하고, 지정된 크기의 바이트를 읽게 하자!
 */
public class BinarySaver {
    public static void main(String[] args) {
        try {
            URL root = new URL("http://www.lolcats.com/images/logo.png");
            saveBinaryFile(root);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // URL 객체를 받아들여서 커넥션을 생성한 다음에,
    // ContentLength 만큼의 byte 배열을 생성해 해당 커넥션의 데이터를 읽어들인다.
    public static void saveBinaryFile(URL u) throws IOException {
        URLConnection uc   = u.openConnection();
        String contentType = uc.getContentType();
        int contentLength  = uc.getContentLength();

        // Content-Type이 text로 시작하거나, Content-Length의 값을 받아오지 못했을 경우
        if (contentType.startsWith("text/") || contentLength == -1) {
            throw new IOException("This is not a binary file.");
        }

        // 사용이 끝나면 리소스를 자동으로 반환한다.
        try (InputStream raw = uc.getInputStream()) {
            InputStream in = new BufferedInputStream(raw);
            byte[] data = new byte[contentLength];
            int offset = 0;

            // 여기서부터 InputStream으로 읽어들이는데, 그 값들을 data 배열 안에 저장한다.
            // offset으로 어디까지 읽었는지, 끊어 읽은 위치를 지정해준다.
            // 이걸 계속 돌리는 이유는, 모든 데이터가 읽히면 좋겠지만 읽히던 중간에 끊길 수도 있으니까
            // 끝까지 읽도록 하기 위해서 계속 확인해주면서 남은 부분을 계속 읽어들이는 것.
            while (offset < contentLength) {
                int bytesRead = in.read(data, offset, data.length - offset);
                // 저장할 대상, 읽기 시작할 위치, 읽어야 할 길이

                if (bytesRead == -1) break;
                offset += bytesRead; // 읽어들인 만큼 offset을 밀어준다.
            }

            // 읽어야 하는 양보다 적게 읽었을 경우 처리
            if (offset != contentLength) {
                throw new IOException("Only read " + offset + " bytes; Expected " + contentLength + " bytes");
            }

            String filename = u.getFile(); // 파일 이름이 아니라 **path**를 읽어오는 것에 주의한다.
            filename = filename.substring(filename.lastIndexOf('/') + 1);
            // 원래 이름은 /images/logo.png니까 마지막 슬래시로 잘라서 경로를 제외한 실제 파일 이름만 가져온다.

            try (FileOutputStream out = new FileOutputStream(filename)) {
                out.write(data);
                out.flush(); // flush는 그냥 버퍼를 비우는 것
            }
            System.out.println("Content-Length " + contentLength);
        }
    }
}
