package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import vo.User;
import util.DBConnectionPool;

public class UserDao {
	/*
	Connection connection;
	
	public void setConnection(Connection connection){
		this.connection = connection;
	}
	*/
	
	DBConnectionPool connPool;
	
	public void setDbConnectionPool(DBConnectionPool connPool){
		this.connPool = connPool;
	}
	
	public int insert(User user) throws Exception{
		Connection connection = null;
		PreparedStatement stmt = null;
		
		try{
			connection = connPool.getConnection();
			stmt = connection.prepareStatement("INSERT INTO users(email,password,name,created_at,updated_at)"
					+ " VALUES (?,?,?,?,?)");
			String timeStamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").
					format(Calendar.getInstance().getTime());
			stmt.setString(1, user.getEmail());
			stmt.setString(2, user.getPassword());
			stmt.setString(3, user.getName());
			stmt.setString(4, timeStamp);
			stmt.setString(5, timeStamp);
			
			return stmt.executeUpdate();
		}catch(Exception e){
			throw e;
		}finally{
			try{if(stmt != null) stmt.close();}catch(Exception e){}
			if(connection != null) connPool.returnConnection(connection);
		}
	}
	
	public User select(int no) throws Exception{
		Connection connection = null;
		Statement stmt = null;
		ResultSet rs = null;
		
		try{
			connection = connPool.getConnection();
			stmt = connection.createStatement();
			rs = stmt.executeQuery("SELECT * from users WHERE id = " + no);
			if(rs.next()){
				return new User().setId(rs.getInt("id")).setEmail(rs.getString("email"))
						.setName(rs.getString("name")).setCreatedAt(rs.getDate("created_at"))
						.setUpdatedAt(rs.getDate("updated_at"));
			}else {
		        throw new Exception("해당 번호의 회원을 찾을 수 없습니다.");
			}
		}catch(Exception e){
			throw e;
		}finally{
			try{if(stmt != null) stmt.close();}catch(Exception e){}
			if(connection != null) connPool.returnConnection(connection);
		}
	}
	
	public List<User> selectList() throws Exception{
		Connection connection = null;
		Statement stmt = null;
		ResultSet rs = null;
		
		try{
			connection = connPool.getConnection();
			stmt = connection.createStatement();
			rs = stmt.executeQuery("SELECT id,name,email,created_at FROM users ORDER BY id ASC");
			
			ArrayList<User> users = new ArrayList<User>();
			
			while(rs.next()){
				users.add(new User().setId(rs.getInt("id")).setEmail(rs.getString("email"))
						.setName(rs.getString("name")).setCreatedAt(rs.getDate("created_at")));
			}
			return users;
		}catch(Exception e){
			throw e;
		}finally{
			try{if(rs != null) rs.close();}catch(Exception e){}
			try{if(stmt != null) stmt.close();}catch(Exception e){}
			if(connection != null) connPool.returnConnection(connection);
		}
	}
	
	public int update(User user) throws Exception{
		Connection connection = null;
		PreparedStatement stmt = null;
		
		try{
			connection = connPool.getConnection();
			stmt = connection.prepareStatement("UPDATE users SET email=?,name=?,updated_at=? WHERE id=?");
			String timeStamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").
					format(Calendar.getInstance().getTime());
			stmt.setString(1, user.getEmail());
			stmt.setString(2, user.getName());
			stmt.setString(3, timeStamp);
			stmt.setInt(4, user.getId());
			
			return stmt.executeUpdate();
		}catch(Exception e){
			throw e;
		}finally{
			try{if(stmt != null) stmt.close();}catch(Exception e){}
			if(connection != null) connPool.returnConnection(connection);
		}
	}
	
	public int delete(int no) throws Exception{
		Connection connection = null;
		Statement stmt = null;
		
		try{
			connection = connPool.getConnection();
			stmt = connection.createStatement();
			return stmt.executeUpdate("DELETE from users WHERE id = " + no);
		}catch(Exception e){
			throw e;
		}finally{
			try{if(stmt != null) stmt.close();}catch(Exception e){}
			if(connection != null) connPool.returnConnection(connection);
		}
	}
	
	public User exist(String email, String password) throws Exception{
		Connection connection = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try{
			connection = connPool.getConnection();
			stmt = connection.prepareStatement("SELECT name,email FROM users WHERE email=? AND password=?");
			stmt.setString(1, email);
			stmt.setString(2, password);
			rs = stmt.executeQuery();
			
			if(rs.next()){
				return new User().setEmail(rs.getString("email")).setName(rs.getString("name"));
			}else {
		        return null;
			}
		}catch(Exception e){
			throw e;
		}finally{
			try{if(rs != null) rs.close();}catch(Exception e){}
			try{if(stmt != null) stmt.close();}catch(Exception e){}
			if(connection != null) connPool.returnConnection(connection);
		}
	}
}
