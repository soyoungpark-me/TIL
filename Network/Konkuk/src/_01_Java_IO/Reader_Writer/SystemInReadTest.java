package _01_Java_IO.Reader_Writer;

import java.io.IOException;

public class SystemInReadTest {
	public static void main(String[] args) {
		int inChar;
		System.out.println("Enter a Character");
		
		try {
			inChar = System.in.read();
			System.out.print("You endtered");
			System.out.println((char)inChar);
		} catch (IOException e) {
			System.out.println("Error reading from user");
		}
	}
}

// System.in.read() 함수의 경우 한 바이트만 읽어들인다.
// 영문은 1byte로 표현할 수 있지만 한글은 그러지 못한다.
// 그러므로 한글을 입력하면 문자가 깨져서 출력되게 된다.