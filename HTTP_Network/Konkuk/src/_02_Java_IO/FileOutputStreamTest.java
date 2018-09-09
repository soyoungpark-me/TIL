package _02_Java_IO;

import java.io.*;

public class FileOutputStreamTest {
	public static void main(String args[]) throws FileNotFoundException, IOException{
		File file = new File("abc.txt");
		
		FileOutputStream fos = new FileOutputStream(FileDescriptor.out);
		FileOutputStream fos1 = new FileOutputStream(file);
		
		byte[] data = {37, 65, 70, 72, (byte)'!', (byte)'\n'};
		fos.write(data);
		fos1.write(data);
		
		// 해당 스트림에 저장되어있던 모든 내용물을 치운다.
		fos.flush();
		fos1.flush();
		
		System.out.println("종료");		
	}
}

// 파일에 입출력하나, 콘솔에 입출력하나 쓰는 방법과 결과는 거의 유사하다.
