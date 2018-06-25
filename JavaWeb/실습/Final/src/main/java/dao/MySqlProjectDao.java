package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import vo.Project;

@Component("projectDao")
public class MySqlProjectDao implements ProjectDao{

	SqlSessionFactory sqlSessionFactory;
	
	@Autowired
	public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory){
		this.sqlSessionFactory = sqlSessionFactory;
	}
	
	public List<Project> selectList(HashMap<String, Object> paramMap) throws Exception{
		SqlSession sqlSession = sqlSessionFactory.openSession();
		
		try{
			return sqlSession.selectList("dao.ProjectDao.selectList", paramMap);
		}finally{
			sqlSession.close();
		}
	}
	
	public int insert(Project project) throws Exception{
		SqlSession sqlSession = sqlSessionFactory.openSession();
		try{
			int count = sqlSession.insert("dao.ProjectDao.insert", project);
			sqlSession.commit();
			return count;
		}finally{
			sqlSession.close();
		}
	}
	
	public Project select(int id) throws Exception{
		SqlSession sqlSession = sqlSessionFactory.openSession();
		try{
			return sqlSession.selectOne("dao.ProjectDao.selectOne", id);
		}finally{
			sqlSession.close();
		}
	}
	
	public int update(Project project) throws Exception{
		SqlSession sqlSession = sqlSessionFactory.openSession();
		try{
			Project original = sqlSession.selectOne("dao.ProjectDao.selectOne", project.getId());
			
			Hashtable<String, Object> paramMap = new Hashtable<String, Object>();
			
			if(!project.getTitle().equals(original.getTitle())){
				paramMap.put("title",  project.getTitle());
			}else if(!project.getContents().equals(original.getContents())){
				paramMap.put("contents", project.getContents());
			}else if(!project.getStart().equals(original.getStart())){
				paramMap.put("start", project.getStart());
			}else if(!project.getEnd().equals(original.getEnd())){
				paramMap.put("end", project.getEnd());
			}else if(project.getState() != original.getState()){
				paramMap.put("state", original.getState());
			}else if(!project.getTags().equals(original.getTags())){
				paramMap.put("tags", original.getTags());
			}
			
			if(paramMap.size() > 0){
				paramMap.put("id", project.getId());
				int count = sqlSession.update("dao.ProjectDao.update", paramMap);
				sqlSession.commit();
				return count;
			}else{
				return 0;
			}			
		}finally{
			sqlSession.close();
		}
	}
	
	public int delete(int id) throws Exception{
		SqlSession sqlSession = sqlSessionFactory.openSession();
		try{
			int count = sqlSession.delete("dao.ProjectDao.delete", id);
			sqlSession.commit();
			return count;
		}finally{
			sqlSession.close();
		}
	}
	/*
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
	*/
}
