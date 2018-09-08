package soyoungpark.me.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

// [주의] 패키지 이름에 전체 패키지가 포함되어 있지 않으면
// 		 스프링이 이 컨트롤러를 인식하지 못한다!
// 스프링은 이 클래스가 컨트롤러인지 아닌지 알 수 없다.
// 이 클래스가 컨드롤러라는것을 알려주기 위해 애노테이션을 붙인다.
@Controller
public class WelcomeController {
	// 어떤 url로 이 메소드를 호출할 것인지 지정해 줘야 한다.
	@GetMapping("/hello")
	public String welcome() {
		// 메소드명은 아무 상관이 없음!
		// 여기서의 welcome은 resource/template 밑에 있는 파일을 가리킨다.
		// .html 확장자는 무시한다. (스프링이 알아서 보내준다)
		return "welcome";
	}
	
	// get을 통해 query를 줄 때는 함수에 인자를 주면 된다.
	@GetMapping("/param")
	public String parameter(String name, Model model) {
		System.out.println("name: " + name);
		// MVC 상에서 컨트롤러가 모델에게 데이터를 저장하기 위해서는
		// addAllAttributes 함수를 이용한다.
		model.addAttribute("name", name);
		
		return "param";
	}
}
