// break문 이용하기

import java.util.Scanner;

public class _06_BreakExample {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner scanner = new Scanner(System.in);
		System.out.println("exit를 입력하면 종료합니다.");
		
		while(true){
			System.out.print(">>");
			String text = scanner.nextLine();
			if(text.equals("exit"))
				break;
		}
		
		System.out.println("종료합니다.");
		scanner.close();	
	}
}
