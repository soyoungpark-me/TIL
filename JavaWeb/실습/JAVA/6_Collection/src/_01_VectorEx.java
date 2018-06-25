import java.util.Vector;

public class _01_VectorEx {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Vector<Integer> v = new Vector<Integer>();
		v.add(5);
		v.add(4);
		v.add(-1);
		
		v.add(2, 100);
		System.out.println("벡터 내의 요소 객체 수 : " + v.size());
		System.out.println("벡터의 현재 용량 : " + v.capacity());
		
		for(int i=0; i<v.size(); i++){
			int n = v.get(i);
			System.out.println(n);
		}
	}

}
