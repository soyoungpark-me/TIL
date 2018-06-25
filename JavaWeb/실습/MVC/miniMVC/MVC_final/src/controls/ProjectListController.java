package controls;

import java.util.Map;

import annotation.Component;
import dao.MySqlProjectDao;

@Component("/projects/list.do")
public class ProjectListController implements Controller{
	MySqlProjectDao projectDao;
	
	public ProjectListController setUserDao(MySqlProjectDao projectDao){
		this.projectDao = projectDao;
		return this;
	}
	
	@Override
	public String execute(Map<String, Object> model) throws Exception {
		model.put("projects", projectDao.selectList());
		return "/projects/ProjectList.jsp";
	}

}
