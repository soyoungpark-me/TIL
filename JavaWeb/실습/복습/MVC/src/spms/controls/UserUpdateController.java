package spms.controls;

import java.util.Map;

import spms.annotation.Component;
import spms.bind.DataBinding;
import spms.dao.UserDaoMySql;
import spms.vo.User;

@Component("users/update.do")
public class UserUpdateController implements Controller, DataBinding {
	UserDaoMySql userDao;
	
	public UserUpdateController setUserDao(UserDaoMySql userDao) {
		this.userDao = userDao;
		return this;
	}

	@Override
	public Object[] getDataBinders() {
		return new Object[]{
			"id", Integer.class,
			"user", spms.vo.User.class
		};
	}
	
	@Override
	public String execute(Map<String, Object> model) throws Exception {
		if (model.get("user") == null) {
			return "/users/UserUpdateForm.jsp";
		} else {
			User user = (User) model.get("user");
			userDao.update(user);
			
			return "redirect:list.do";
		}
	}
}
