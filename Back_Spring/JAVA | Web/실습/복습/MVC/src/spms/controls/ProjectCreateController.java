package spms.controls;

import java.util.Map;

import spms.annotation.Component;
import spms.bind.DataBinding;
import spms.dao.ProjectDao;
import spms.vo.Project;

@Component("/project/new.do")
public class ProjectCreateController implements Controller, DataBinding {
	ProjectDao projectDao;
	
	public ProjectCreateController setProjectDao(ProjectDao projectDao) {
		this.projectDao = projectDao;
		return this;
	}
	
	@Override
	public Object[] getDataBinders() {
		return new Object[] {
			"project", spms.vo.Project.class
		};
	}

	@Override
	public String execute(Map<String, Object> model) throws Exception {
		Project project = (Project) model.get("project");
		
		if (project.getTitle() == null) {
			return "/project/ProjectForm.jsp";
		} else {
			projectDao.insert(project);
			return "redirect:list.do";
		}
	}
	
}
