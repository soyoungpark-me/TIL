package soyoungpark.me.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

import soyoungpark.me.vo.User;

@Controller
public class UserController {
	// 오는 요청이 Post이기 때문에 getMapping이 아니라 PostMapping이다.
	// body 값을 하나하나 인자로 받아오지 않고, vo를 만들어서 가져올 수 있다.
	// 이럴 땐, vo에 setter와 getter 함수가 정의되어 있어야 한다.
	
	@PostMapping("/create")
	public String create(User user) {
		System.out.println("user : " + user);
		return "welcome";
	}

}
