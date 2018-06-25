import java.util.*;

public class _09_HashMapDicEx {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		HashMap<String, String> dic = new HashMap<String, String>();
		
		dic.put("baby", "아기");
		dic.put("love", "사랑");
		dic.put("apple", "사과");
		
		Set<String> keys = dic.keySet();
		Iterator<String> it = keys.iterator();
		
		while(it.hasNext()){
			String key = it.next();
			String value = dic.get(key);
			System.out.print("(" + key + ", " + value + ")");
		}
		
		System.out.println();
		
		Scanner scanner = new Scanner(System.in);
		
		for(int i=0; i<3; i++){
			System.out.print("찾고 싶은 단어는? ");
			String eng = scanner.next();
			
			String kor = dic.get(eng);
			if(kor == null)
				System.out.println(eng + "는 없는 단어입니다.");
			else
				System.out.println(kor);
		}
	}
}
