package spms.vo;

public class User {
	protected int id;
	protected String name;
	protected String email;
	protected String password;
	
	public int getId() {
		return id;
	}
	
	public User setId(int id) {
		this.id = id;
		return this;
	}
	
	public String getName() {
		return name;
	}
	
	public User setName(String name) {
		this.name = name;
		return this;
	}
	
	public String getEmail() {
		return email;
	}
	
	public User setEmail(String email) {
		this.email = email;
		return this;
	}
	
	public String getPassword() {
		return password;
	}
	
	public User setPassword(String password) {
		this.password = password;
		return this;
	}
}
