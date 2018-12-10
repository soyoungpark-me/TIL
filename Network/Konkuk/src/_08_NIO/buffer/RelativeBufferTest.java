package _08_NIO.buffer;

import java.nio.ByteBuffer;

public class RelativeBufferTest {
    public static void main(String[] args) {
        ByteBuffer buffer = ByteBuffer.allocate(10);    // 10바이트만큼의 버퍼를 할당

        System.out.print("Init Position: " + buffer.position());
        System.out.print(", Init Limit: " + buffer.limit());
        System.out.println(", Init Capacity: " + buffer.capacity());
        // Init Position: 0, Init Limit: 10, Init Capacity: 10

        buffer.mark(); // 버퍼의 현재 위치에 마킹한다.

        buffer.put((byte) 10).put((byte) 11).put((byte) 12);
        // but은 put을 한 다음의 버퍼를 반환하기 때문에 체이닝이 가능하다.
        System.out.println("Before Reset, Position: " + buffer.position()); // Before Reset, Position: 3

        buffer.reset(); // 마킹해 둔 곳으로 위치 (position)를 변경한다.
        System.out.println("After Reset, Position: " + buffer.position());  // After Reset, Position: 0

        System.out.println("Value: " + buffer.get() + ", Position: " + buffer.position());
        System.out.println("Value: " + buffer.get() + ", Position: " + buffer.position());
        System.out.println("Value: " + buffer.get() + ", Position: " + buffer.position());
        System.out.println("Value: " + buffer.get() + ", Position: " + buffer.position());

         /**
            Value: 10, Position: 1
            Value: 11, Position: 2
            Value: 12, Position: 3
            Value: 0, Position: 4
         */
    }
}
