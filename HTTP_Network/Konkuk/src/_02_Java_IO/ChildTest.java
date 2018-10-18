package _02_Java_IO;

class Parent2 {
	int i = 7;	public int get() {
		return i;
	}
}

class Child2 extends Parent2 {
	int i = 5;
	public int get() {
		return i;
	}
}

public class ChildTest {
	public static void print(Parent2 p){
		System.out.println(p.i);
		System.out.println(p.get());
	}
	
	public static void main(String[] args) {
		Parent2 p = new Parent2();
		System.out.println("----- 1 -----");
		System.out.println(p.i);		// 7
		System.out.println(p.get());	// 7
		
		Child2 c = new Child2();
		System.out.println("----- 2 -----");
		System.out.println(c.i);		// 5
		System.out.println(c.get());	// 5
		
		// Child 인스턴스를 생성한 다음에 Parent2 부모 클래스로 형변환.
		// 부모와 자식이 같은 멤버 변수를 가지고 있을 땐 부모 멤버 변수에 접근되고,
		// 메소드의 경우에는 자식 메소드가 접근된다.
		
		Parent2 p2 = new Parent2();
		System.out.println("----- 3 -----");
		System.out.println(p2.i);		// 7
		System.out.println(p2.get());	// 7
		
		System.out.println("----- 4 -----");
		// print의 인자로 들어가면 Parent2로 형변환되므로
		// 멤버 변수의 경우 Parent의 멤버 변수가 되고, 메소드는 Child의 메소드가 된다.
		print(c);						// 7, 5
		print(p2);						// 7, 7
	}
}
