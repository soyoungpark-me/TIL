// 인자로 배열을 전달하는 예시

public class ArrayParameter {
	static void replaceSpace(char a[]){
		for(int i=0; i<a.length; i++)
			if(a[i] == ' ')
				a[i] = ',';
	}
	
	static void printCharArray(char a[]){
		for(int i=0; i<a.length; i++)
			System.out.print(a[i]);
		System.out.println();
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		char c[] = {'I', ' ', 'a', 'm', ' ', 'a', ' ', 's', 't', 'u', 'd', 'e', 'n', 't'};
		printCharArray(c);
		replaceSpace(c);
		printCharArray(c);
	}

}
