import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

public class FinalTerm2018 {
    public static void main(String[] args) {
        final String STRING_HOST = "203.252.148.148";
        final String NUMBER_HOST = "114.70.22.1";
        final int STRING_PORT = 8888;
        final int NUMBER_PORT = 9999;

        Socket socket = null;

        // 1. 문자열 출력하기
        try {
            socket = new Socket(STRING_HOST, STRING_PORT);

            InputStream in = socket.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(in, "UTF-8"));

            for (String line = reader.readLine(); line != null; line = reader.readLine()) {
                System.out.println(line);
            }
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (socket != null) try { socket.close(); } catch (IOException e) {}
        }

        // 2. 숫자 출력하기
        /**
         * 기존에 쓰던 BufferedWriter의 경우에는 문자를 쓰는 데에 최적화되어 있기 떄문에...
         *
         * int형 수를 전송하기 위해서는
         * DataOutputStream으로 싸준 뒤에 writeInt() 함수를 이용해야 한다.
         * 출력할 때에도 마찬가지로, int 값을 받아와야 하기 때문에
         * DataInputStream으로 싸준 뒤에 readInt() 함수를 이용해야 한다.
         */
        try {
            socket = new Socket(NUMBER_HOST, NUMBER_PORT);
            OutputStream out = socket.getOutputStream();
            DataOutputStream dos = new DataOutputStream(out);

            dos.writeInt(1004);
            dos.flush();
            socket.shutdownOutput();

            InputStream in = socket.getInputStream();
            DataInputStream dis = new DataInputStream(in);
            System.out.println(dis.readInt());
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (socket != null) try { socket.close(); } catch (IOException e) {}
        }
    }
}
