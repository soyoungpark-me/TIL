package spms.dao;

import java.util.List;

import spms.vo.User;

public interface UserDao {
	List<User> selectList() throws Exception;
	int insert(User user) throws Exception;
	int delete(int id) throws Exception;
	public User selectOne(int id) throws Exception;
	int update(User user) throws Exception;
	User exist(String email, String password) throws Exception;
}
