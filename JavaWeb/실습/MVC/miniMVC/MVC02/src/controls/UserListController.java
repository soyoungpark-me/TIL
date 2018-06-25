package controls;

import java.util.Map;
import dao.MySqlUserDao;

public class UserListController implements Controller{
	MySqlUserDao userDao;
	
	public UserListController setUserDao(MySqlUserDao userDao){
		this.userDao = userDao;
		return this;
	}
	
	@Override
	public String execute(Map<String, Object> model) throws Exception {
		//UserDao userDao = (UserDao)model.get("userDao");
		model.put("users", userDao.selectList());
		return "/users/UserList.jsp";
	}

}
