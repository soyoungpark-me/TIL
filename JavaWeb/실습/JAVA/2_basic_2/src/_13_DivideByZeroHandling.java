// 0으로 나누는 예외에 대처하는 try-catch 블록 만들기

import java.util.Scanner;
public class _13_DivideByZeroHandling {

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
		
		try{
			System.out.println(divided + "를 " + divisor + "로 나누면 몫은 " + divided/divisor + "입니다");
		}catch(ArithmeticException e){
			System.out.println("0으로 나눌 수 없습니다.");
		}finally{
			scanner.close();
		}
		
		
	}

}
