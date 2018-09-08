import java.util.Vector;

class Point{
	private int x,y;
	public Point(int x, int y){
		this.x = x;
		this.y = y;
	}
	
	public String toSring(){
		return "(" + x + ", " + y + ")";
	}
}
public class _02_PointVectorEx {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Vector<Point> v = new Vector<Point>();
		
		v.add(new Point(2, 3));
		v.add(new Point(-5, 20));
		v.add(new Point(30, -8));
		
		v.remove(1);
		
		for(int i=0; i<v.size(); i++){
			Point p = v.get(i);
			System.out.println(p);
		}
	}

}
