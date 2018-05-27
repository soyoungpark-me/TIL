package exam.test10;

import java.util.Date;
import java.util.Map.Entry;
import java.util.Properties;

public class Tire {
	String maker;
	Properties spec;
	Date created_at;
	
	public String getMaker(){
		return maker;
	}
	
	public void setMaker(String maker){
		this.maker = maker;
	}
	
	public Properties getSpec(){
		return spec;
	}
	
	public void setSpec(Properties spec){
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
		StringBuffer specInfo = new StringBuffer();
		if(spec != null){
			for(Entry<Object, Object> entry : spec.entrySet()){
				specInfo.append(entry.getKey() + " : " + entry.getValue() + ", ");
			}
		}
		return "[Tire : " + maker + ", " + specInfo.toString() + 
				((created_at != null)?(", ") + created_at.toString() : "") + "]";
	}
}
