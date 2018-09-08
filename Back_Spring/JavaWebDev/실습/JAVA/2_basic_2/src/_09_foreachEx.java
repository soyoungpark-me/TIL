// for-each 문 활용

public class _09_foreachEx {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int n[] = {1, 2, 3, 4, 5};
		int sum = 0;
		
		for(int k:n){
			System.out.print(k + " ");
			sum += k;
		}
		System.out.println("합은 " + sum);
		
		String f[] = {"사과", "배", "바나나", "체리", "딸기", "포도"};
		for(String s:f)
			System.out.print(s + " ");
	}

}
