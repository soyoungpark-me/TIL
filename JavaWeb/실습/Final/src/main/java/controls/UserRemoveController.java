package controls;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bind.DataBinding;
import dao.MySqlUserDao;

@Component("/users/delete.do")
public class UserRemoveController implements Controller, DataBinding {
	MySqlUserDao userDao;
	
	@Autowired
	public UserRemoveController setUserDao(MySqlUserDao userDao){
		this.userDao = userDao;
		return this;
	}
	
	@Override
	public Object[] getDataBinders() {
		return new Object[]{
			"no", Integer.class
		};
	}
	
	@Override
	public String execute(Map<String, Object> model) throws Exception {
		Integer no = (Integer)model.get("no");
		//MySqlUserDao userDao = (MySqlUserDao) model.get("userDao");
		
		userDao.delete(no);
		
		return "redirect:list.do";
	}
}
