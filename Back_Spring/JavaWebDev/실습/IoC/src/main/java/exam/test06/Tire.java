package exam.test06;

import java.util.Date;

public class Tire {
	String maker;
	String spec;
	Date created_at;
	
	public String getMaker(){
		return maker;
	}
	
	public void setMaker(String maker){
		this.maker = maker;
	}
	
	public String getSpec(){
		return spec;
	}
	
	public void setSpec(String spec){
		this.spec = spec;
	}
	
	public Date getCreatedAt(){
		return created_at;
	}
	
	public void setCreatedAt(Date created_at){
		this.created_at = created_at;
	}
	
	@Override
	public String toString(){
		return "[Tire : " + maker + ", " + spec + 
				((created_at != null)?(", ") + created_at.toString() : "") + "]";
	}
}
