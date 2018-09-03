class Shape{
	public void draw(){
		System.out.println("Shape");
	}
}

class Line extends Shape{
	public void draw(){
		System.out.println("Line");
	}
}

class Rect extends Shape{
	public void draw(){
		System.out.println("Rect");
	}
}

class Circle extends Shape{
	public void draw(){
		System.out.println("Circle");
	}
}

public class MethodOverridingEx {
	static void paint(Shape p){
		p.draw();
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Line line = new Line();
		paint(line);
		
		paint(new Shape());
		paint(new Rect());
		paint(new Circle());
		paint(new Line());
	}

}
