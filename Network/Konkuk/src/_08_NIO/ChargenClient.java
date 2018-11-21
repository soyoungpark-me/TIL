package _08_NIO;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.SocketChannel;
import java.nio.channels.WritableByteChannel;

public class ChargenClient {
    public static int DEFAULT_PORT = 2001;

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
            SocketAddress address = new InetSocketAddress(args[0], port); // 1. 주소, 포트로 SocketAddress 생성
            SocketChannel client = SocketChannel.open(address);           // 2. 해당 주소로 SocketChannel 연결

            ByteBuffer buffer = ByteBuffer.allocate(74); // 74 만큼의 버퍼를 할당한다.
            WritableByteChannel out = Channels.newChannel(System.out); // TODO 이게 뭘까!

            FileOutputStream file = new FileOutputStream("test.txt");
            FileChannel fout = file.getChannel(); // 파일로 써주기

            /**
             * 만약 74byte를 읽었다면 position은 끝에 가 있다.
             * flip()을 사용해서 74byte를 읽을 수 있는 최대치로 정해 두고, position을 처음으로 되돌린다.
             * 그리고 buffer에 write를 하고 나면 해당 버터의 position이 다시 맨 끝으로 가게 된다.
             * 이 후에는 clear()를 해준다. 최대치를 정해줄 필요가 없고, 새로 입력을 받게 되기 때문에 양을 예측할 수 없다.
             */
            while (client.read(buffer) != -1) { // 버퍼에 계속 읽어들인다.
                buffer.flip();  // flip은 버퍼가 write 할 한계점(?)까지 정해준다.
//                out.write(buffer);
                fout.write(buffer);
                buffer.clear(); // 옮겨간 포지션을 다시 0으로 땡겨준다.
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
