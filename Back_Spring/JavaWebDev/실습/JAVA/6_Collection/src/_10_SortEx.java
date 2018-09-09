import java.util.*;

public class _10_SortEx {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String[] sample = {"I", "am", "very", "hungry"};
		
		List<String> list = Arrays.asList(sample);
		System.out.println(list);
		
		Collections.sort(list);
		System.out.println(list);
	}

}
