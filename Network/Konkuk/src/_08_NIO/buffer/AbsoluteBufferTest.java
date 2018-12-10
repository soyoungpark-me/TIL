package _08_NIO.buffer;

import java.nio.ByteBuffer;

public class AbsoluteBufferTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ByteBuffer buf = ByteBuffer.allocate(10);
		System.out.print("Init Position: " + buf.position());
		System.out.print(", Init Limit: " + buf.limit());
		System.out.println(", Init Capacity: " + buf.capacity());
		
		buf.put(3, (byte)3);
		buf.put(4, (byte)4);
		buf.put(5, (byte)5);
		
		System.out.println("Value: " + buf.get(3) + ", Position: " + buf.position());
		System.out.println("Value: " + buf.get(4) + ", Position: " + buf.position());
		System.out.println("Value: " + buf.get(5) + ", Position: " + buf.position());
		System.out.println("Value: " + buf.get(9) + ", Position: " + buf.position());


	}

}
