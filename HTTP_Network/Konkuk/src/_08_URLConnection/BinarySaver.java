package _08_URLConnection;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

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
        URLConnection uc = u.openConnection();
        String contentType = uc.getContentType();
        int contentLength = uc.getContentLength();

        // Content-Type이 text로 시작하거나, Content-Length의 값을 받아오지 못했을 경우
        if (contentType.startsWith("text/") || contentLength == -1) {
            throw new IOException("This is not a binary file.");
        }

        try (InputStream raw = uc.getInputStream()) {
            InputStream in = new BufferedInputStream(raw);
            byte[] data = new byte[contentLength];
            int offset = 0;

            // 여기서부터 InputStream으로 읽어들이는데, 그 값들을 data 배열 안에 저장한다.
            // offset으로 어디까지 읽었는지, 끊어 읽은 위치를 지정해준다.
            // 이걸 계속 돌리는 이유는, 모든 데이터가 읽히면 좋겠지만 읽히던 중간에 끊길 수도 있으니까
            // 끝까지 읽도록 하기 위해서 계속 확인해주면서 남은 부분을 계속 읽어들이는 것.
            while(offset < contentLength) {
                int bytesRead = in.read(data, offset, data.length - offset);
                // 저장할 대상, 읽기 시작할 위치, 읽어야 할 길이
                if (bytesRead == -1) break;
                offset += bytesRead;
            }

            if (offset != contentLength) {
                throw new IOException("Only read " + offset + " bytes; Expected " + contentLength + " bytes");
            }

            String filename = u.getFile(); // 파일 이름이 아니라 path를 읽어오는 것에 주의한다.
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
