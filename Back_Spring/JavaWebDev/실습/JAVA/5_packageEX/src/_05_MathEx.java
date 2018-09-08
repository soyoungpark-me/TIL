// Math 클래스 활용하기

public class _05_MathEx {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println(Math.abs(-3.14));
		System.out.println(Math.sqrt(3.0));
		System.out.println(Math.exp(2));
		System.out.println(Math.round(3.14));
		
		System.out.print("이번주 행운의 번호는 ");
		for(int i=0; i<5; i++)
			System.out.print((int)(Math.random()*45 + 1) + " ");
	}

}
