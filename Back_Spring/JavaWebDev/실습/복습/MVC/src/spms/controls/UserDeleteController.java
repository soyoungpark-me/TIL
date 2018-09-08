package spms.controls;

import java.util.Map;

import spms.annotation.Component;
import spms.bind.DataBinding;
import spms.dao.UserDaoMySql;

@Component("/users/delete.do")
public class UserDeleteController implements Controller, DataBinding {
	UserDaoMySql userDao;
	
	public UserDeleteController setUserDao(UserDaoMySql userDao) {
		this.userDao = userDao;
		return this;
	}
	
	@Override
	public Object[] getDataBinders() {
		return new Object[]{
			"id", Integer.class
		};
	}
	
	@Override
	public String execute(Map<String, Object> model) throws Exception {
		Integer id = Integer.parseInt((String) model.get("id"));		
		userDao.delete(id);
		
		return "redirect:list.do";
	}
}
