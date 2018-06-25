// do-while문 이용하기
public class _03_DoWhileSample {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		char a = 'a';
		
		do{
			System.out.print(a);
			a = (char)(a + 1);
		}while(a<='z');
	}

}
