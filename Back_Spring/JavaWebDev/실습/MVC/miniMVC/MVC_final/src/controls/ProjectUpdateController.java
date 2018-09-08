package controls;

import java.util.Map;

import annotation.Component;
import bind.DataBinding;
import dao.MySqlProjectDao;
import vo.Project;

@Component("/projects/update.do")
public class ProjectUpdateController implements Controller, DataBinding{
	MySqlProjectDao projectDao;
	
	public ProjectUpdateController setUserDao(MySqlProjectDao projectDao){
		this.projectDao = projectDao;
		return this;
	}
	
	@Override
	public Object[] getDataBinders() {
		return new Object[]{
			"no", Integer.class,
			"project", vo.Project.class
		};
	}

	@Override
	public String execute(Map<String, Object> model) throws Exception {
		Project project = (Project) model.get("project");
		int no = (int) model.get("no");
		
		if(project.getTitle() == null){
			Project detailInfo = projectDao.select(no);
			model.put("project", detailInfo);
			return "/projects/ProjectUpdate.jsp";
		}else{
			project.setId(no);
			projectDao.update(project);
			
			return "redirect:list.do";
		}
	}

}
