package controls;

import java.util.HashMap;
import java.util.Map;

import annotation.Component;
import bind.DataBinding;
import dao.MySqlProjectDao;

@Component("/projects/list.do")
public class ProjectListController implements Controller, DataBinding{
	MySqlProjectDao projectDao;
	
	public ProjectListController setUserDao(MySqlProjectDao projectDao){
		this.projectDao = projectDao;
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
		HashMap<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("orderCond", model.get("orderCond"));
		model.put("projects", projectDao.selectList(paramMap));
		return "/projects/ProjectList.jsp";
	}
}
