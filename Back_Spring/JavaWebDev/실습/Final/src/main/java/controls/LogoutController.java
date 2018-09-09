package controls;

import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Component;

@Component("/auth/logout.do")
public class LogoutController implements Controller {	
	@Override
	public String execute(Map<String, Object> model) throws Exception {
		HttpSession session = (HttpSession)model.get("session");
		session.invalidate();
		return "redirect:login.do";
	}

}
