package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.sql.DataSource;

import annotation.Component;
import vo.Project;

@Component("projectDao")
public class MySqlProjectDao implements ProjectDao{
	DataSource ds;
	
	public void setDataSource(DataSource ds){
		this.ds = ds;
	}
	
	@Override
	public List<Project> selectList() throws Exception {
		Connection connection = null;
		Statement stmt = null;
		ResultSet rs = null;
		
		try{
			connection = ds.getConnection();
			stmt = connection.createStatement();
			rs = stmt.executeQuery("SELECT * FROM projects ORDER BY id DESC");
			
			ArrayList<Project> projects = new ArrayList<Project>();
			
			while(rs.next()){
				projects.add(new Project().setId(rs.getInt("id")).setTitle(rs.getString("title"))
						.setContents(rs.getString("title")).setStart(rs.getDate("start"))
						.setEnd(rs.getDate("end")).setState(rs.getInt("state"))
						.setCreatedAt(rs.getDate("created_at")).setTags(rs.getString("tags")));
			}			
			return projects;
		}catch(Exception e){
			throw e;
		}finally{
			try{if(rs != null) rs.close();}catch(Exception e){}
			try{if(stmt != null) stmt.close();}catch(Exception e){}
			try{if(connection != null) connection.close();}catch(Exception e){}
		}
	}

	@Override
	public int insert(Project project) throws Exception {
		Connection connection = null;
		PreparedStatement stmt = null;
		
		try{
			connection = ds.getConnection();
			stmt = connection.prepareStatement("INSERT INTO projects "
					+ "(title, contents, start, end, state, created_at, tags) "
					+ "VALUES(?, ?, ?, ?, 0, ?, ?)");
			String timeStamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").
					format(Calendar.getInstance().getTime());
			stmt.setString(1, project.getTitle());
			stmt.setString(2, project.getContents());
			stmt.setDate(3, new java.sql.Date(project.getStart().getTime()));
			stmt.setDate(4, new java.sql.Date(project.getEnd().getTime()));
			stmt.setString(5, timeStamp);
			stmt.setString(6, project.getTags());
			
			return stmt.executeUpdate();			
		}catch(Exception e){
			throw e;
		}finally{
			try{if(stmt != null) stmt.close();}catch(Exception e){}
			try{if(connection != null) connection.close();}catch(Exception e){}
		}
	}

	@Override
	public Project select(int no) throws Exception {
		Connection connection = null;
		Statement stmt = null;
		ResultSet rs = null;
		
		try{
			connection = ds.getConnection();
			stmt = connection.createStatement();
			rs = stmt.executeQuery("SELECT title, contents, start, end, start, state, tags "
					+ "from projects where id = " + no);
			
			if(rs.next()){
				return new Project().setId(no).setTitle(rs.getString("title"))
						.setContents(rs.getString("contents")).setStart(rs.getDate("start"))
						.setEnd(rs.getDate("end")).setState(rs.getInt("state"));
				
			}else {
		        throw new Exception("해당 번호의 회원을 찾을 수 없습니다.");
			}
		}catch(Exception e){
			throw e;
		}finally{
			try{if(stmt != null) stmt.close();}catch(Exception e){}
			try{if(connection != null) connection.close();}catch(Exception e){}
		}
	}

	@Override
	public int update(Project project) throws Exception {
		Connection connection = null;
		PreparedStatement stmt = null;
		
		try{
			connection = ds.getConnection();
			stmt = connection.prepareStatement("UPDATE projects SET title=?, contents=?, start=?, end=?, tags=? WHERE id = ?");
			stmt.setString(1, project.getTitle());
			stmt.setString(2, project.getContents());
			stmt.setDate(3, new java.sql.Date(project.getStart().getTime()));
			stmt.setDate(4, new java.sql.Date(project.getEnd().getTime()));
			stmt.setString(5, project.getTags());
			stmt.setInt(6, project.getId());
			
			return stmt.executeUpdate();			
		}catch(Exception e){
			throw e;
		}finally{
			try{if(stmt != null) stmt.close();}catch(Exception e){}
			try{if(connection != null) connection.close();}catch(Exception e){}
		}
	}

	@Override
	public int delete(int no) throws Exception {
		Connection connection = null;
		Statement stmt = null;
		
		try{
			connection = ds.getConnection();
			stmt = connection.createStatement();
			return stmt.executeUpdate("DELETE FROM projects WHERE id = " + no);
		}catch(Exception e){
			throw e;
		}finally{
			try{if(stmt != null) stmt.close();}catch(Exception e){}
			try{if(connection != null) connection.close();}catch(Exception e){}
		}
	}
}
