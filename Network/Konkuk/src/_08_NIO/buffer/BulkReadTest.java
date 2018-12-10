package _08_NIO.buffer;

import java.nio.ByteBuffer;

public class BulkReadTest {
    public static void main(String[] args) {
        ByteBuffer buffer = ByteBuffer.allocate(10);

        buffer.put((byte) 0).put((byte) 1).put((byte) 2).put((byte) 3).put((byte) 4);
        buffer.mark(); // 현재 position에 마킹한다
        // 0 1 2 3 4 [] [] [] [] []
        // position: 5

        buffer.put((byte) 5).put((byte) 6).put((byte) 7).put((byte) 8).put((byte) 9);
        buffer.reset(); // 마킹해둔 곳으로 position을 바꾼다
        // 0 1 2 3 4 5 6 7 8 9
        // position: 5

        byte[] b = new byte[15]; // 15짜리 바이트 배열 생성

        int size = buffer.remaining(); // position부터 limit까지 남은 갯수 반환 (size = 5)
        if (b.length < size) size = b.length;

        System.out.println("Position: " + buffer.position() + ", Limit: " + buffer.limit()); // Position: 5, Limit: 10
        buffer.get(b, 0, size);
        // b 배열을 0부터 size만큼 배열으로 값을 옮긴다!
        // 현재 포지션은 5이부터 5부터 5개가 배열으로 이동하게 된다.

        System.out.println("Position: " + buffer.position() + ", Limit: " + buffer.limit()); // Position: 10, Limit: 10
        doSomething(b, size);
    }

    public static void doSomething(byte[] b, int size) {
        for (int i = 0; i < size; i++) {
            System.out.println("byte = " + b[i]);
        }
    }
}
