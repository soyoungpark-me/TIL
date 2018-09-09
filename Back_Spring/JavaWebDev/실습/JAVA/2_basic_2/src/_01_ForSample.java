// for문 이용하기
public class _01_ForSample {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int i, sum=0;
		
		for(i=1; i<=10; i++){
			sum+=i;
			System.out.println(i);
			
			if(i<=9)
				System.out.print("+");
			else{
				System.out.print("=");
				System.out.println(sum);
			}
		}
	}

}
