package _07_Socket;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

public class DaytimeServer {
    public final static int PORT = 2000;

    public static void main(String[] args) {
        // 먼저 ServerSocket 객체를 생성하는데, 포트 번호만 지정해주면 된다.
        try (ServerSocket server = new ServerSocket(PORT)) {
            // Socket이 열린 후, 특정 작업을 다 마친 다음에는 while으로 다시 돌아와 새 연결을 기다린다.
            while (true) {
                // accept() 메소는 실제 클라이언트의 req를 기다리며,
                // req를 받고 나면 연결된 Socket을 반환해준다. 연결된 이후 작업은 try 문 안에 써주면 된다.
                try (Socket connection = server.accept()) {
                    System.out.println("New Connection is opened");
                    Writer out = new OutputStreamWriter(connection.getOutputStream());
                    Date now = new Date();
                    // OutputStreamWriter에 써주면 클라이언트에서 이를 받아 처리할 것.
                    out.write(now.toString() + "\r\n");
                    out.flush();
                    connection.close();
                } catch (IOException e) {}
            }
        } catch (IOException e) {
            System.err.println(e);
        }
    }
}

/*
accept() 함수는 실행되면 실제로 클라이언트의 요청이 들어올 때까지 block된 상태로 대기한다.
만약 동시에 여러 클라이언트에서 연결 요청이 들어오면...
block 되어있기 때문에 이 요청을 큐에 쌓아놓고 하나씩 순차적으로 처리해야 한다.
그런데 한 클라이언트의 속도가 굉장히 느리다면...!
    이 클라이언트 때문에 뒤에 대기중인, 나중에 연결 요청을 보낸 클라이언트들 또한
    오랜 시간 동안 응답을 받기 위해 기다려야 한다.
    -> 굉장히 비효율적이다!!!
이를 해결하기 위해 쓰는 방법이 [멀티쓰레드]이다.
    커넥션마다 새로 프로세스를 생성하는 방법도 있긴 하지만,
    프로세스 간에 메모리를 공유하지 않고 독립적으로 쓰기 때문에 메모리를 너무 많이 잡아먹는다.
    -> 커넥션이 들어올 때마다 새로운 쓰레드를 만드는 멀티쓰레드 방법을 많이 사용한다.
 */