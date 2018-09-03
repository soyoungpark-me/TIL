package controls;

import java.util.HashMap;
import java.util.Map;

import annotation.Component;
import bind.DataBinding;
import dao.MySqlUserDao;

@Component("/users/list.do")
public class UserListController implements Controller, DataBinding{
	MySqlUserDao userDao;
	
	public UserListController setUserDao(MySqlUserDao userDao){
		this.userDao = userDao;
		return this;
	}

	@Override
	public Object[] getDataBinders() {
		return new Object[]{
			"orderCond", String.class	
		};
	}
	
	@Override
	public String execute(Map<String, Object> model) throws Exception {
		//UserDao userDao = (UserDao)model.get("userDao");
		HashMap<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("orderCond", model.get("orderCond"));
		model.put("users", userDao.selectList(paramMap));
		return "/users/UserList.jsp";
	}

}
