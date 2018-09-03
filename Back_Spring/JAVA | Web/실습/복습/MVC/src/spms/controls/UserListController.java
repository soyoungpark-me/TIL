package spms.controls;

import java.util.Map;

import spms.annotation.Component;
import spms.controls.Controller;
import spms.dao.UserDaoMySql;

@Component("/users/list.do")
public class UserListController implements Controller{
	UserDaoMySql userDao;
	
	public UserListController setUserDao(UserDaoMySql userDao) {
		this.userDao = userDao;
		return this;
	}
	
	@Override
	public String execute(Map<String, Object> model) throws Exception{			
		model.put("users", userDao.selectList());
		return "/users/UserList.jsp";
	}
}
