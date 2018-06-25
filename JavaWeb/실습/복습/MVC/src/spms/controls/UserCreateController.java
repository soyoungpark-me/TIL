package spms.controls;

import java.util.Map;

import spms.annotation.Component;
import spms.bind.DataBinding;
import spms.dao.UserDaoMySql;
import spms.vo.User;

@Component("/users/new.do")
public class UserCreateController implements Controller, DataBinding{
	UserDaoMySql userDao;
	
	public UserCreateController setUserDao(UserDaoMySql userDao) {
		this.userDao = userDao;
		return this;
	}
	
	public Object[] getDataBinders() {
		return new Object[] {
			"user", spms.vo.User.class
		};
	}
	
	@Override
	public String execute(Map<String, Object> model) throws Exception {
		User user = (User)model.get("user");
		
		if (model.get("user") == null) {
			return "/users/UserForm.jsp";
		} else {
			userDao.insert(user);
			return "redirect:list.do";
		}
	}

}
