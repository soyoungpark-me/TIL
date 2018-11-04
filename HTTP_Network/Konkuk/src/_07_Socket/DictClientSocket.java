package _07_Socket;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

public class DictClientSocket {
    public static void main(String[] args) {
        String host = "dict.org"; // path 없이 host만 넣어줘야 한다.

        try {
            Socket soc = new Socket(host, 2628); // host와 port로 연결을 시작한다.
            OutputStream out = soc.getOutputStream();
            Writer writer = new OutputStreamWriter(out, "UTF-8");
            writer = new BufferedWriter(writer);
            // OutputStream > OutputStreamWriter(utf-8 인코딩) > BufferedWriter

            InputStream in = soc.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(in, "UTF-8"));
            // InputStream > InputStreamReader(utf-8 인코딩) > BufferedReader

            String request = "DEFINE fd-eng-lat black"; // syntax: DEFINE DB query

            // 소켓에 연결된 writer를 통해 직접 서버에 쏜다. response를 받아올 것임!
            writer.write(request);
            writer.flush();
            soc.shutdownOutput(); // output을 닫아준다.

            // 받아온 response를 출력한다.
            for (String line = reader.readLine(); line != null; line = reader.readLine()) {
                System.out.println(line);
            }

            soc.close();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            System.err.println("Cannot found the host at " + host);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
