import java.util.Scanner;

public class _09_Twenties {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner scanner = new Scanner(System.in);
		
		System.out.println("나이를 입력하세요 : ");
		int age = scanner.nextInt();
		if((age >= 20) && (age<30)){
			System.out.println("20대입니다.");
			System.out.println("20대라서 행복합니다.");
		}else{
			System.out.println("20대가 아닙니다.");
		}
		
		scanner.close();
	}
}
