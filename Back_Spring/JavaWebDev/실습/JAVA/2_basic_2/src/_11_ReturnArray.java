// 배열 리턴
// 일차원 정수 배열을 생성해 리턴하는 메소드 생성
// 이 메소드로부터 배열을 전달 받음
public class _11_ReturnArray {
	
	static int[] makeArray(){
		int temp[] = new int[4];
		
		for(int i=0; i<temp.length; i++){
			temp[i] = i;
		}
		return temp;
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int intArray[];
		
		intArray = makeArray();
		
		for(int i=0; i<intArray.length; i++){
			System.out.println(intArray[i] + " ");
		}
	}

}
