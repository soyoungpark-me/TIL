// 이중 중첩 이용하기
public class _04_NestedLoop {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		for(int i=1; i<10; i++){
			for(int j=1; j<10; j++){
				System.out.print(i + "*" + j + "=" + i*j);
				System.out.print("\t");
			}
			System.out.println();
		}
	}

}
