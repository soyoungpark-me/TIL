import java.util.*;

public class _05_IteratorEx {

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
		
		Iterator<Integer> it = v.iterator();
		
		while(it.hasNext()){
			int n = it.next();
			System.out.println(n);
		}
		
		int sum = 0;
		it = v.iterator();
		
		while(it.hasNext()){
			int n = it.next();
			sum += n;
		}
		
		System.out.println("벡터에 있는 정수의 합 : " + sum);
	}
}
