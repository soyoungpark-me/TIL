package _02_Java_IO;

public class InheritanceTest {
	public static void main(String[] args) {
		FirstChild fc = new FirstChild();
		System.out.println(fc.read());
		
		SecondChild sc = new SecondChild();
		System.out.println(sc.read());
		
		ThirdChild tc1 = new ThirdChild(fc);
		System.out.println(tc1.read());
		
		ThirdChild tc2 = new ThirdChild(sc);
		System.out.println(tc2.read());
	}
}

class Parent{
	public String read() {
		return "Parent 입니다";
	}
}

class FirstChild extends Parent {
	public String read() {
		return super.read() + ": firstChild";
	}
}

class SecondChild extends Parent {
	public String read() {
		return super.read() + ": secondChild";
	}
}

class ThirdChild extends Parent {
	Parent p;
	
	public ThirdChild(Parent p) {
		// Parent를 생성자 인자로 받아서 멤버 변수에 지정한다.
		this.p = p;
	}
	
	public String read() {
		// ThirdChild에서 read하면 내부적으로
		// 멤버 변수에 가지고 있는 Parent의 read를 사용한다.
		return super.read() + ": thirdChild";
	}
}