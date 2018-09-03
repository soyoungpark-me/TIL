// StringTokenizer를 이용해 문자열 분리하기

import java.util.StringTokenizer;

public class _04_StringTokenizer {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String query = "name=soyoung&address=seoul&age=23";
		StringTokenizer st = new StringTokenizer(query, "&");
		
		int n = st.countTokens();
		System.out.println("토큰 개수 = " +n);
		
		while(st.hasMoreTokens()){
			String token = st.nextToken();
			System.out.println(token);
		}
	}
}
