package spms.dao;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;

import spms.annotation.Component;
import spms.vo.User;

@Component("userDao")
public class UserDaoMySql implements UserDao {
	DataSource ds;
	
	public void setDataSource(DataSource ds) {
		this.ds = ds;
	}
	
	public List<User> selectList() throws Exception {
		Connection connection = null;
		Statement stmt = null;
		ResultSet rs = null;
		
		try {
			connection = ds.getConnection();
			stmt = connection.createStatement();
			rs = stmt.executeQuery("SELECT * FROM users ORDER BY id ASC");
			
			ArrayList<User> users = new ArrayList<User>();
			
			while(rs.next()) {
				users.add(new User().setId(rs.getInt("id"))
					.setName(rs.getString("name"))
					.setEmail(rs.getString("email")));
			}
			return users;
		} catch (Exception e) {
			throw e;
		} finally {
			try { if (rs != null) rs.close(); } catch (Exception e) {}
			try { if (stmt != null) stmt.close(); } catch (Exception e) {}
			try { if (connection != null) connection.close(); } catch (Exception e) {}
		}
	}
	
	public int insert(User user) throws Exception {
		Connection connection = null;
		PreparedStatement stmt = null;
		
		try {
			connection = ds.getConnection();
			stmt = connection.prepareStatement(
					"INSERT INTO users (email, name, password) VALUES(?, ?, ?)");			
			stmt.setString(1, user.getEmail());
			stmt.setString(2, user.getEmail());
			stmt.setString(3, user.getPassword());
			
			return stmt.executeUpdate();
		} catch (Exception e) {
			throw e;
		} finally {
			try { if (stmt != null) stmt.close(); } catch (Exception e) {}
			try { if (connection != null) connection.close(); } catch (Exception e) {}
		}
	}
	
	public int delete(int id) throws Exception {
		Connection connection = null;
		PreparedStatement stmt = null;
		
		try {
			connection = ds.getConnection();
			stmt = connection.prepareStatement("DELETE FROM users WHERE id = " + id);
			
			return stmt.executeUpdate();
		} catch (Exception e) {
			throw e;
		} finally {
			try { if (stmt != null) stmt.close(); } catch (Exception e) {}
			try { if (connection != null) connection.close(); } catch (Exception e) {}
		}
	}
	
	public User selectOne(int id) throws Exception {
		Connection connection = null;
		Statement stmt = null;
		ResultSet rs = null;
		
		try {
			connection = ds.getConnection();
			stmt = connection.createStatement();
			rs = stmt.executeQuery("SELECT * FROM users WHERE id = " + id);
			if (rs.next()) {
				return new User().setId(id)
					.setEmail(rs.getString("email"))
					.setName(rs.getString("name"));
			} else {
		        throw new Exception("해당 번호의 회원을 찾을 수 없습니다.");
	        }			
			
		} catch (Exception e) {
			throw e;
		} finally {
			try { if (rs != null) rs.close(); } catch (Exception e) {}
			try { if (stmt != null) stmt.close(); } catch (Exception e) {}
			try { if (connection != null) connection.close(); } catch (Exception e) {}
		}
	}
	
	public int update(User user) throws Exception {
		Connection connection = null;
		PreparedStatement stmt = null;
		
		try {
			connection = ds.getConnection();
			stmt = connection.prepareStatement(
				"UPDATE users SET email = ?, name = ? WHERE id = ?");
			stmt.setString(1, user.getEmail());
			stmt.setString(2, user.getName());
			stmt.setInt(3, user.getId());
			
			return stmt.executeUpdate();
		} catch (Exception e) {
			throw e;
		} finally {
			try { if (stmt != null) stmt.close(); } catch (Exception e) {}
			try { if (connection != null) connection.close(); } catch (Exception e) {}
		}
	}
	
	public User exist(String email, String password) throws Exception {
		Connection connection = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
			connection = ds.getConnection();
			stmt = connection.prepareStatement(
				"SELECT * FROM users WHERE email = ? AND password = ?");
			stmt.setString(1, email);
			stmt.setString(2, email);
			
			rs = stmt.executeQuery();
			
			if (rs.next()) {
				return new User().setEmail(rs.getString("email"))
					.setName(rs.getString("name"));
			} else {
				return null;
			}
		} catch (Exception e) {
			throw e;
		} finally {
			try { if (rs != null) rs.close(); } catch (Exception e) {}
			try { if (stmt != null) stmt.close(); } catch (Exception e) {}
			try { if (connection != null) connection.close(); } catch (Exception e) {}
		}
	}
}
