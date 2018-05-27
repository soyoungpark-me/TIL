package spms.controls;

import java.util.Map;

import javax.servlet.http.HttpSession;

import spms.annotation.Component;
import spms.bind.DataBinding;
import spms.dao.UserDaoMySql;
import spms.vo.User;

@Component("/auth/login.do")
public class LoginController implements Controller, DataBinding {
	UserDaoMySql userDao;
	
	@Override
	public Object[] getDataBinders() {
		return new Object[]{
			"loginInfo", spms.vo.User.class
		};
	}

	public LoginController setUserDao(UserDaoMySql userDao) {
		this.userDao = userDao;
		return this;
	}
	
	@Override
	public String execute(Map<String, Object> model) throws Exception {
		User loginInfo = (User)model.get("loginInfo");
	      
		if (loginInfo.getEmail() == null) {
			return "/auth/LoginForm.jsp";
		} else {
			User user = userDao.exist(
		          loginInfo.getEmail(), 
		          loginInfo.getPassword());
		      
			if (user != null) {
				HttpSession session = (HttpSession)model.get("session");
				session.setAttribute("user", user);
				
				return "redirect:../users/list.do";
			} else {
				return "/auth/LoginFail.jsp";
			}
			
		}
	}
}
