package controls;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bind.DataBinding;
import dao.MySqlUserDao;
import vo.User;

@Component("/users/update.do")
public class UserUpdateController implements Controller, DataBinding{
	MySqlUserDao userDao;
	
	@Autowired
	public UserUpdateController setUserDao(MySqlUserDao userDao){
		this.userDao = userDao;
		return this;
	}
	
	@Override
	public Object[] getDataBinders() {
		return new Object[]{
			"no", Integer.class,
			"user", vo.User.class
		};
	}
	
	@Override
	public String execute(Map<String, Object> model) throws Exception {
		User user = (User)model.get("user");
		Integer no = (Integer)model.get("no");
		
		if(user.getEmail() == null){
			User detailInfo = userDao.select(no);
			model.put("user", detailInfo);
			
			return "/users/UserUpdate.jsp";
		}else{
			user.setId(no);
			userDao.update(user);
			return "redirect:list.do";
		}
		/*
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
		*/
	}
}
