// /와 % 산술 연산자 응용

import java.util.Scanner;

public class _04_ArithmeticOperator {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner scanner = new Scanner(System.in);
		
		System.out.println("정수를 입력하세요");
		int time = scanner.nextInt();
		int second = time % 60;
		int minute = (time/60)%60;
		int hour = (time/60)%60;
		
		System.out.print(time + "초는 ");
		System.out.print(hour + "시간, ");
		System.out.print(minute + "분, ");
		System.out.println(second + "초입니다.");
		
		scanner.close();		
	}
}
