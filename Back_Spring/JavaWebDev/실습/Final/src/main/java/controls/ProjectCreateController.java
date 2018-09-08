package controls;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bind.DataBinding;
import dao.MySqlProjectDao;
import vo.Project;

@Component("/projects/create.do")
public class ProjectCreateController implements Controller, DataBinding{
	MySqlProjectDao projectDao;
	
	@Autowired
	public ProjectCreateController setUserDao(MySqlProjectDao projectDao){
		this.projectDao = projectDao;
		return this;
	}
	
	@Override
	public Object[] getDataBinders() {
		return new Object[]{
			"project", vo.Project.class
		};
	}

	@Override
	public String execute(Map<String, Object> model) throws Exception {
		Project project = (Project) model.get("project");
		
		if(project.getTitle() == null){
			return "/projects/ProjectCreate.jsp";
		}else{
			projectDao.insert(project);
			return "redirect:list.do";
		}
	}

}
