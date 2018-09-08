import java.util.*;

public class _04_LinkedListEx {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		LinkedList <String> list = new LinkedList<String>();
		list.add("MILK");
		list.add("BREAD");
		list.add("BUTTER");
		list.add(1, "APPLE");
		list.add(2, "GRAPE");
		list.remove(3);
		for(int i=0; i<list.size(); i++)
			System.out.println(list.get(i));
	}

}
