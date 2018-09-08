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

import vo.User;

@Component("userDao")
public class MySqlUserDao implements UserDao{
	
	SqlSessionFactory sqlSessionFactory;
	
	@Autowired
	public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory){
		this.sqlSessionFactory = sqlSessionFactory;
	}
	public int insert(User user) throws Exception{
		SqlSession sqlSession = sqlSessionFactory.openSession();
		try{
			int count = sqlSession.insert("dao.UserDao.insert", user);
			sqlSession.commit();
			return count;
		}finally{
			sqlSession.close();
		}
	}
	
	public User select(int no) throws Exception{
		SqlSession sqlSession = sqlSessionFactory.openSession();
		try{
			return sqlSession.selectOne("dao.UserDao.selectOne", no);
		}finally{
			sqlSession.close();
		}
	}
	
	public List<User> selectList(HashMap<String, Object>paramMap) throws Exception{
		SqlSession sqlSession = sqlSessionFactory.openSession();
		try{
			return sqlSession.selectList("dao.UserDao.selectList", paramMap);
		}finally{
			sqlSession.close();
		}
	}
	
	public int update(User user) throws Exception{
		SqlSession sqlSession = sqlSessionFactory.openSession();
		try{
			User original = sqlSession.selectOne("dao.UserDao.selectOne", user.getId());
			Hashtable<String, Object> paramMap = new Hashtable<String, Object>();
			
			if(!user.getEmail().equals(original.getEmail())){
				paramMap.put("email", user.getEmail());
			}else if(!user.getName().equals(original.getName())){
				paramMap.put("name", user.getName());
			}
			
			if(paramMap.size() > 0){
				paramMap.put("id", original.getId());
				int count = sqlSession.update("dao.UserDao.update", paramMap);
				sqlSession.commit();
				return count;
			}else{
				return 0;
			}			
		}finally{
			sqlSession.close();
		}
	}
	
	public int delete(int no) throws Exception{
		SqlSession sqlSession = sqlSessionFactory.openSession();
		try{
			int count = sqlSession.delete("dao.UserDao.delete", no);
			sqlSession.commit();
			return count;
		}finally{
			sqlSession.close();
		}
	}
	
	public User exist(String email, String password) throws Exception{
		SqlSession sqlSession = sqlSessionFactory.openSession();
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("email", email);
		map.put("password", password);
		
		try{
			return sqlSession.selectOne("dao.UserDao.exist", map);
		}finally{
			sqlSession.close();
		}
	}
}
