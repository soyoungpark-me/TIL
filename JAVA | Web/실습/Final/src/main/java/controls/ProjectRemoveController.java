package controls;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bind.DataBinding;
import dao.MySqlProjectDao;

@Component("/projects/delete.do")
public class ProjectRemoveController implements Controller, DataBinding{

	MySqlProjectDao projectDao;
	
	@Autowired
	public ProjectRemoveController setUserDao(MySqlProjectDao projectDao){
		this.projectDao = projectDao;
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
		int no = (int) model.get("no");
		
		projectDao.delete(no);
		return "redirect:list.do";
	}

}
