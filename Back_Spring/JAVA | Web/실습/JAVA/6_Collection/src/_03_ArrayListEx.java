import java.util.*;

public class _03_ArrayListEx {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ArrayList<String> a = new ArrayList<String>();
		
		Scanner scanner = new Scanner(System.in);
		for(int i=0; i<4; i++){
			System.out.print("이름을 입력하세요 >> ");
			String s = scanner.next();
			a.add(s);
		}
		
		for(int i=0; i<a.size(); i++){
			String name = a.get((i));
			System.out.println(name + " ");
		}
	}

}
