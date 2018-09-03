package controls;

import java.util.Map;
import dao.MySqlUserDao;

public class UserRemoveController implements Controller {
	MySqlUserDao userDao;
	
	public UserRemoveController setUserDao(MySqlUserDao userDao){
		this.userDao = userDao;
		return this;
	}
	
	@Override
	public String execute(Map<String, Object> model) throws Exception {
		Integer no = (Integer)model.get("no");
		MySqlUserDao userDao = (MySqlUserDao) model.get("userDao");
		
		userDao.delete(no);
		
		return "redirect:list.do";
	}
}
