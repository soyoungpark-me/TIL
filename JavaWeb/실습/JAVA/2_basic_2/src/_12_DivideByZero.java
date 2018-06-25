// 0으로 나누면 예외가 발생해 응용프로그램이 강제로 종료된다. 

import java.util.Scanner;

public class _12_DivideByZero {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner scanner = new Scanner(System.in);
		
		int divided, divisor;
		
		System.out.print("나뉨수를 입력하세요 : ");
		divided = scanner.nextInt();
		
		System.out.print("나눗수를 임력하세요 : ");
		divisor = scanner.nextInt();
		
		System.out.println(divided + "를 " + divisor + "로 나누면 몫은 " + divided/divisor + "입니다");
	}

}
