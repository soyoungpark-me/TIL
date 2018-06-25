package controls;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bind.DataBinding;
import dao.MySqlUserDao;
import vo.User;

@Component("/users/create.do")
public class UserCreateController implements Controller, DataBinding{
	MySqlUserDao userDao;
	
	@Autowired
	public UserCreateController setUserDao(MySqlUserDao userDao){
		this.userDao = userDao;
		return this;
	}

	@Override
	public Object[] getDataBinders() {
		return new Object[]{
				"user", vo.User.class
		};
	}
	
	@Override
	public String execute(Map<String, Object> model) throws Exception {
		User user = (User)model.get("user");
		
		if(user.getEmail() == null){
			return "/users/UserCreate.jsp";
		}else{
			userDao.insert(user);
			return "redirect:list.do";
		}
		/*
		if(model.get("user") == null){
			return "/users/UserCreate.jsp";
		}else{
			MySqlUserDao userDao = (MySqlUserDao) model.get("userDao");
			User user = (User) model.get("user");
			userDao.insert(user);
			
			return "redirect:list.do";
		}
		*/
	}
}
