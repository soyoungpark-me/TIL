package _08_NIO;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.SocketChannel;
import java.nio.channels.WritableByteChannel;

public class ChargenClient {
    public static int DEFAULT_PORT = 19;

    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Usage: java ChargenClient host [port]");
            return;
        }

        int port;
        try {
            port = Integer.parseInt(args[1]);
        } catch (RuntimeException ex) {
            port = DEFAULT_PORT;
        }

        try {
            SocketAddress address = new InetSocketAddress(args[0], port);
            SocketChannel client = SocketChannel.open(address);

            ByteBuffer buffer = ByteBuffer.allocate(74); // 74 만큼의 버퍼를 할당한다.
            WritableByteChannel out = Channels.newChannel(System.out); // TODO 이게 뭘까!

            while (client.read(buffer) != -1) {
                buffer.flip();  // flip은 버퍼가 write 할 한계점(?)까지 정해준다.
                out.write(buffer);
                buffer.clear(); // 옮겨간 포지션을 다시 0으로 땡겨준다.
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
