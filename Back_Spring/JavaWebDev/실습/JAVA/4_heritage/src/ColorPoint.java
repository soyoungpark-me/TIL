class ColorPoint extends Point{
	private String color;
	
	ColorPoint(int x, int y, String color){
		super(x, y);
		this.color = color;
	}
	
	void setColor(String color){
		this.color = color;
	}
	void showColorPoint(){
		System.out.print("["+ color + "] ");
		showPoint();
	}
}
