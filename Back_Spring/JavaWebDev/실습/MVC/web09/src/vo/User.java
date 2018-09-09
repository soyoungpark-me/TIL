package vo;

import java.util.Date;

public class User {
	protected int 		id;
	protected String 	name;
	protected String 	email;
	protected String 	password;
	protected Date		created_at;
	protected Date		updated_at;
	
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
	public Date getCreatedAt() {
		return created_at;
	}
	public User setCreatedAt(Date created_at) {
		this.created_at = created_at;
		return this;
	}
	public Date getUpdatedAt() {
		return updated_at;
	}
	public User setUpdatedAt(Date updated_at) {
		this.updated_at = updated_at;
		return this;
	}
}
