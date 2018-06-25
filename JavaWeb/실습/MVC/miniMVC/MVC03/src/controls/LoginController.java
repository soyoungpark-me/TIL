package controls;

import java.util.Map;

import javax.servlet.http.HttpSession;

import bind.DataBinding;
import dao.MySqlUserDao;
import vo.User;

public class LoginController implements Controller, DataBinding {
	MySqlUserDao userDao;
	
	public LoginController setUserDao(MySqlUserDao userDao){
		this.userDao = userDao;
		return this;
	}
	
	@Override
	public Object[] getDataBinders() {
		return new Object[]{
			"loginInfo", vo.User.class
		};
	}
	
	@Override
	public String execute(Map<String, Object> model) throws Exception {
		User loginInfo = (User)model.get("loginInfo");
		if(loginInfo.getEmail() == null){
			return "/auth/LoginForm.jsp";
		}else{
			User user = userDao.exist(loginInfo.getEmail(), loginInfo.getPassword());
			
			if(user != null){
				HttpSession session = (HttpSession) model.get("session");
				session.setAttribute("user", user);
				return "redirect:../users/list.do";
			}else{
				return "/auth/LoginFail.jsp";
			}
		}
		/*
		if(model.get("loginInfo") == null){
			return "/auth/LoginForm.jsp";
		}else{
			MySqlUserDao userDao = (MySqlUserDao)model.get("userDao");
			User loginInfo = (User)model.get("loginInfo");
			
			User user = userDao.exist(loginInfo.getEmail(), loginInfo.getPassword());
			
			if(user != null){
				HttpSession session = (HttpSession)model.get("session");
				session.setAttribute("user", user);
				return "redirect:../users/list.do";
			}else{
				return "/auth/LoginFail.jsp";
			}
		}
		*/
	}
}
