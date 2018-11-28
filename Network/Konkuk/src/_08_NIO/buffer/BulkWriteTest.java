package _08_NIO.buffer;

import java.nio.ByteBuffer;

public class BulkWriteTest {
    public static void main(String[] args) {
        ByteBuffer buf = ByteBuffer.allocate(10);   // 10바이트짜리 버퍼를 할당한다.
        buf.position(5);    // 다음에 읽거나 쓰는 부분을 5로 설정한다.
        buf.mark();
        System.out.println("1) Position: " + buf.position() + ", Limit: " + buf.limit());

        byte[] b = new byte[15];                // 15바이트 배열 생성 후...
        for (int i = 0; i < b.length; i++) {    // 배열의 각 인덱스에 값을 넣어둔다.
            b[i] = (byte) i;
        }

        int size = buf.remaining();             // 현재 배열이 남은 자리가 있는지 확인한다.
        if (b.length < size) {
            size = b.length;
        }

        System.out.println("2) Position: " + buf.position() + ", Limit: " + buf.limit());
        buf.put(b, 0, size);

        System.out.println("3) Position: " + buf.position() + ", Limit: " + buf.limit());
        doSomething(buf, size);
    }

    public static void doSomething(ByteBuffer buf, int size) {
        for (int i=0; i<size; i++) {
            System.out.println("byte = " + buf.get());
        }
    }
}

/**
 * Exception in thread "main" java.nio.BufferUnderflowException가 발생하는 이유
 * OverflowException  : 위치가 제한 크기까지 도달한 상태에서 데이터를 삽입하려고 했을 때
 * UnderflowException : 제한 크기 이후에 위치한 데이터를 읽어오려 했을 때
 *
 */
