package dao;

import java.util.HashMap;
import java.util.List;
import vo.User;

public interface UserDao {
	List<User> selectList(HashMap<String, Object>paramMap) throws Exception;
	int insert(User user) throws Exception;
	int delete(int no) throws Exception;
	User select(int no) throws Exception;
	int update(User user) throws Exception;
	User exist(String email, String password) throws Exception;
}
