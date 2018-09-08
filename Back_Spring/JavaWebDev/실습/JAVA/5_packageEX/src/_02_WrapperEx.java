public class _02_WrapperEx {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println(Character.toLowerCase('A'));
		char c1='4', c2='F';
		
		if(Character.isDigit(c1))
			System.out.println(c1+"은(는) 숫자");
		if(Character.isAlphabetic(c2))
			System.out.println(c2+"은(는) 영문자");
		
		System.out.println(Integer.parseInt("28"));
		System.out.println(Integer.toString(28));
		System.out.println(Integer.toBinaryString(28));
		System.out.println(Integer.bitCount(28));
		
		Integer i = new Integer(28);
		System.out.println(i.doubleValue());
		
		Double d = new Double(3.14);
		System.out.println(d.toString());
		System.out.println(Double.parseDouble("3.14"));
		
		boolean b = (4>3);
		System.out.println(Boolean.toString(b));
		System.out.println(Boolean.parseBoolean("false"));
	}
}
