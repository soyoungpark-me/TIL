package spms.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import spms.vo.Project;

public class ProjectDaoMySql implements ProjectDao {
	DataSource ds;
	
	public void setDataSource(DataSource ds) {
		this.ds = ds;
	}
	
	@Override
	public List<Project> selectList() throws Exception {
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		
		try {
			conn = ds.getConnection();
			stmt = conn.createStatement();
			rs = stmt.executeQuery("SELECT * FROM projects ORDER BY id DESC");
			
			ArrayList<Project> projects = new ArrayList<Project>();
			
			while(rs.next()) {
				projects.add(new Project()
					.setId(rs.getInt("id"))
					.setTitle(rs.getString("title"))
					.setContents(rs.getString("contents"))
					.setStart(rs.getDate("start"))
					.setEnd(rs.getDate("end"))
					.setState(rs.getInt("state")));
			}
			
			return projects;
			
		} catch (Exception e) {
			throw e;
		} finally {
			try { if (rs != null) rs.close(); } catch (Exception e) {}
			try { if (stmt != null) stmt.close(); } catch (Exception e) {}
			try { if (conn != null) conn.close(); } catch (Exception e) {}
		}
	}

	@Override
	public int insert(Project project) throws Exception {
		Connection conn = null;
		PreparedStatement stmt = null;
		
		try {
			conn = ds.getConnection();
			stmt = conn.prepareStatement("INSERT INTO projects "
				+ "(title, contents, start, end, state, created_at, tags) "
				+ "VALUES (?, ?, ?, ?, 0, NOW(), ?");
			
			stmt.setString(1, project.getTitle());
			stmt.setString(2, project.getContents());
			stmt.setDate(3, new java.sql.Date(project.getStart().getTime()));
			stmt.setDate(3, new java.sql.Date(project.getEnd().getTime()));
			stmt.setString(5, project.getTags());
			
			return stmt.executeUpdate();
			
		} catch (Exception e) {
			throw e;
		} finally {
			try { if (stmt != null) stmt.close(); } catch (Exception e) {}
			try { if (conn != null) conn.close(); } catch (Exception e) {}
		}
	}
}
