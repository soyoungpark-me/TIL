package controls;

import java.util.Map;

import dao.MySqlUserDao;
import vo.User;

public class UserUpdateController implements Controller{
	MySqlUserDao userDao;
	
	public UserUpdateController setUserDao(MySqlUserDao userDao){
		this.userDao = userDao;
		return this;
	}
	
	@Override
	public String execute(Map<String, Object> model) throws Exception {
		MySqlUserDao userDao = (MySqlUserDao) model.get("userDao");
		
		if(model.get("user") == null){
			Integer no = (Integer)model.get("no");
			User user = userDao.select(no);
			model.put("user", user);
			
			return "/users/UserUpdate.jsp";
		}else{
			User user = (User) model.get("user");
			userDao.update(user);
			
			return "redirect:list.do";
		}
	}
}
