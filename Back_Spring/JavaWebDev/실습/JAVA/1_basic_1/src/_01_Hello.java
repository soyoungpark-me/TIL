// public 클래스는 한 파일에 하나만!
// public 클래스의 이름은 파일 이름과 같아야 함!

public class _01_Hello {

	/**
	 * @param args
	 */
	public static int sum(int n, int m){
		return n+m;
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		int i = 20;
		int s;
		char a;
		
		s = sum(i, 20);
		a = '?';
		
		System.out.println(a);
		System.out.println("Hello");
		System.out.println(s);	

	}

}
