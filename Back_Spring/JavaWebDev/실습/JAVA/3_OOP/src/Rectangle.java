public class Rectangle {
	int width;
	int height;
	int getArea(){
		return width*height;
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Rectangle rect = new Rectangle();
		rect.width = 4;
		rect.height = 5;
		
		System.out.println("사각형의 면적은 = " + rect.getArea());
	}

}
