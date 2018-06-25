package controls;

import java.util.Map;

import dao.MySqlUserDao;
import vo.User;

public class UserCreateController implements Controller{
	MySqlUserDao userDao;
	
	public UserCreateController setUserDao(MySqlUserDao userDao){
		this.userDao = userDao;
		return this;
	}
	
	@Override
	public String execute(Map<String, Object> model) throws Exception {
		if(model.get("user") == null){
			return "/users/UserCreate.jsp";
		}else{
			MySqlUserDao userDao = (MySqlUserDao) model.get("userDao");
			User user = (User) model.get("user");
			userDao.insert(user);
			
			return "redirect:list.do";
		}
	}
}
