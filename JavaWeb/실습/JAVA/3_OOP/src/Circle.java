// Circle 클래스의 객체 생성 및 활용 

public class Circle {
	int radius;
	String name;
	
	public Circle(){
		radius = 1; name = "";
	}
	
	public Circle(int r){
		radius = r; name = "";
	}
	
	public Circle(int r, String n){
		radius = r; name = n;
	}
	
	public double getArea(){
		return 3.14*radius*radius;
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Circle pizza;
		pizza = new Circle(10, "자바피자");
		
		double area = pizza.getArea();
		System.out.println(pizza.name + "의 면적은 " + area);
		
		Circle donut = new Circle();
		donut.radius = 2;
		donut.name = "자바도넛";
		area = donut.getArea();
		System.out.println(donut.name + "의 면적은 " + area);
	}

}
