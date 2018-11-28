package _08_NIO.buffer;

import java.nio.ByteBuffer;

public class DataConversionTest {
    public static void main(String[] args) {
        ByteBuffer buf = ByteBuffer.allocate(16);
        int i=0;
        while (buf.hasRemaining()) { // 자리가 남아 있다면
            buf.put((byte) i);       // 0부터 15에 해당하는 바이트를 버퍼에 쓴다.
            i++;
        }
        buf.flip();                  // 버퍼의 포지션을 0으로 옮긴다.

        System.out.println(buf);
        showBuffer(buf, "int");     // int는    4바이트 > int로 해석하면 4개
        showBuffer(buf, "char");    // char는   2바이트 > 8개
        showBuffer(buf, "float");   // float은  4바이트 > 4개
        showBuffer(buf, "long");    // double은 8바이트 > 2개
    }

    /**
     * 받아온 버퍼와 타입으로, 버퍼의 내용을 해당 타입에 맞게 출력한다.
     * @param buf: 1부터 15까지의 숫자가 담긴 버퍼
     * @param type: 해당 버퍼를 해석하고자 하는 타입
     */
    public static void showBuffer(ByteBuffer buf, String type) {
        if (type.equals("int")) {
            while (buf.hasRemaining()) {
                System.out.println(buf.getInt());
            }
            System.out.println();
            buf.flip();
        } else if (type.equals("char")) {
            while (buf.hasRemaining()) {
                System.out.println(buf.getChar());
            }
            System.out.println();
            buf.flip();
        } else if (type.equals("float")) {
            while (buf.hasRemaining()) {
                System.out.println(buf.getFloat());
            }
            System.out.println();
            buf.flip();
        } else if (type.equals("long")) {
            while (buf.hasRemaining()) {
                System.out.println(buf.getDouble());
            }
            System.out.println();
            buf.flip();
        }
    }
}

/*
[출력 결과]

66051
67438087
134810123
202182159


ȃ
Ѕ
؇
ࠉ
਋
఍
ฏ

9.2557E-41
1.5636842E-36
4.1238743E-34
1.0866475E-31

1.40159977307889E-309
5.924543410270741E-270


Process finished with exit code 0

 */
