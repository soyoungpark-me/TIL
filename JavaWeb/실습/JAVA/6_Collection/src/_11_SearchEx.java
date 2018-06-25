import java.util.*;

public class _11_SearchEx {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int key = 50;
		List<Integer> list = new ArrayList<Integer>();
		for(int i=0; i<100; i++)
			list.add(i);
		
		int index = Collections.binarySearch(list, key);
		System.out.println("탑색의 반환값 = " + index);
	}

}
