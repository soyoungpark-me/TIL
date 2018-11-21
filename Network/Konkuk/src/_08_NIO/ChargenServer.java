package _08_NIO;

import java.nio.*;
import java.nio.channels.*;
import java.net.*;
import java.util.*;
import java.io.IOException;

public class ChargenServer {

    public static int DEFAULT_PORT = 19;

    public static void main(String[] args) {
        int port;
        try {
            port = args.length > 0 ? Integer.parseInt(args[0]) : 2001;
        } catch (RuntimeException ex) {
            port = DEFAULT_PORT;
        }
        System.out.println("Listening for connections on port " + port);

        // 버퍼를 옮기고, 해당 내용을 클라이언트 채널에 써야 한다.
        // 해당 버퍼는 재사용한다.
        byte[] rotation = new byte[95*2];   // 실제로 클라이언트에 써주게 될, 캐릭터들을 적어 놓은 배열
        for (byte i = ' '; i <= '~'; i++) { // 버퍼 인덱스가 0부터 94까지
            rotation[i -' '] = i;           // 첫 번째 한 줄과
            rotation[i + 95 - ' '] = i;     // 그 다음에 등장해야 하는 한 줄
        }

        ServerSocketChannel serverChannel;
        Selector selector;
        try {
            serverChannel = ServerSocketChannel.open();                 // 제일 먼저 ServerSocketChannel부터 연다.
            ServerSocket ss = serverChannel.socket();                   // 채널에서 먼저 소켓을 가져온다.
            InetSocketAddress address = new InetSocketAddress(port);    // 바인딩할 포트를 지정해준다.
            ss.bind(address);                                           // 만든 소켓에 포트를 바인딩해준다.
            serverChannel.configureBlocking(false);                     // 실제로 nonblocking 형식으로 돈다.
            selector = Selector.open();                                 // 셀렉터를 생성하고,
            serverChannel.register(selector, SelectionKey.OP_ACCEPT);   // OP_ACCEPT로 등록!
        } catch (IOException ex) {
            ex.printStackTrace();
            return;
        }

        /* 준비가 된 채널을 가져오는 과정 */
        while (true) {
            try {
                selector.select();  // 이를 통해 준비가 되어 있는 채널들을 확인할 수 있다.
            } catch (IOException ex) {
                ex.printStackTrace();
                break;
            }

            Set<SelectionKey> readyKeys = selector.selectedKeys();
            // selectedKeys()로 준비된 키들을 Set 형태로 가져올 수 있다.
            // 여기엔 등록한 객체들이 key로 나오게 된다.

            Iterator<SelectionKey> iterator = readyKeys.iterator(); // 준비된 키들을 쓰기 위해 iterator를 생성한다.

            while (iterator.hasNext()) {            // iterator가 더 존재하는지 확인한다.
                SelectionKey key = iterator.next(); // key에는 아무런 객체나 붙일 수 있다. 여기서는 버퍼를 붙인다.
                iterator.remove();                  // 혹시라도 key를 중복으로 사용하는 경우를 막기 위해 선택적으로 붙인 부분!

                try {
                    // 내가 가져온 key가 Accept인지, Write인지, Read인지에 따라서 해줄 내용이 다르게 된다.

                    if (key.isAcceptable()) {
                        /**
                         * Accept > ServerSocketChannel
                         * 위에서 ServerSocketChannel을 OP_ACCEPT로 등록해줬다.
                         * serverChannel.register(selector, SelectionKey.OP_ACCEPT)
                         * 이 키에 딱히 붙은 것은 없다.
                         */
                        ServerSocketChannel server = (ServerSocketChannel) key.channel();   // 먼저 채널을 가져온 뒤에,
                        SocketChannel client = server.accept(); // [중요] 실제로 클라이언트와 연결한 SocketChannel을 생성한다!
                        System.out.println("Accepted connection from " + client);

                        client.configureBlocking(false); // 클라이언트 SocketChannel을 nonblocking으로 설정

                        // [중요] 여기서 생성된 SocketChannel을 셀렉터에 WRITE로 등록해준다.
                        SelectionKey key2 = client.register(selector, SelectionKey.OP_WRITE);
                        ByteBuffer buffer = ByteBuffer.allocate(74); // 74 바이트짜리 버퍼를 생성한다.
                        buffer.put(rotation, 0, 72);
                        // put 함수로 buffer에 저장한다. 0(offset)에서부터 출발해서 72(length)만큼을 해당 버퍼에 저장한다.

                        buffer.put((byte) '\r');
                        buffer.put((byte) '\n');                     // 뒤에 CRLF를 붙여준다.
                        buffer.flip();                               // 버퍼의 position을 맨 앞으로 돌린다.
                        key2.attach(buffer);                         // 해당 버퍼를 key에 붙여준다.

                        /**
                         * 만약 클라이언트가 하나인 상황이라고 가정할 경우,
                         * 한 번 accept되어 클라이언트와 소켓이 연결되었을 때 이 부분으로 다시 들어올 일은 없다.
                         * 새로은 클라이언트가 연결 요청을 했을 때 들어오게 된다.
                         */

                    } else if (key.isWritable()) {
                        /**
                         * Write > SocketChannel
                         * 위에서 SocketChannel을 client.register(selector, SelectionKey.OP_WRITE)로 등록한다.
                         * 74바이트 만큼 저장된 버퍼가 여기에 붙어 있다.
                         */
                        SocketChannel client = (SocketChannel) key.channel();
                        ByteBuffer buffer = (ByteBuffer) key.attachment();  // 키에 붙여 놓은 버퍼를 가져온다.

                        /**
                         * attach를 한 상태로 buffer를 가져오면, 초기에는
                         * position과 limit 사이에 엘리멘트가 존재하게 되어 if 문을 벗어날 것!
                         * 따라서 0부터 72까지 읽은 것이 제일 먼저 출력되
                         */

                        if (!buffer.hasRemaining()) {
                            // 만약 버퍼에 내용이 남아 있지 않다면? position과 limit 사이에 아무 것도 없다면?
                            // write과 read 과정이 모두 끝났을 경우에는 여기에 들어오게 된다.

                            buffer.rewind();            // 포지션을 0으로 돌린다.
                            int first = buffer.get();   // 현재 포지션이 가지고 있는 값을 가져온다.
                            buffer.rewind();            // 포지션을 0으로 맞춰준다. 버퍼 안의 데이터를 바꿔줄 준비를 하는 것!

                            int position = first - ' ' + 1; // 첫 번째 바이트에서 ' '를 빼줘야 버퍼 인덱스가 0부터 시작하게 된다.

                            System.out.println("first: " + first + "   position: " + position);

                            buffer.put(rotation, position, 72); // 첫 번째 캐릭터부터 72개만큼 써준다.
                            buffer.put((byte) '\r');
                            buffer.put((byte) '\n');    // 끝에 CRLF를 붙여준다.

                            buffer.flip();
                        }
                        // 버퍼에 내용이 남아있다면 if문에 들어가지 않고 그냥 write를 바로 한다.
                        client.write(buffer);
                    }
                } catch (IOException ex) {
                    // 익셉션이 발생했을 때에는 key를 cancel해 주고, 해당 키의 채널도 닫아 줘야 한다.
                    key.cancel();
                    try {
                        key.channel().close();
                    }
                    catch (IOException cex) {}
                }
            }
        }
    }
}