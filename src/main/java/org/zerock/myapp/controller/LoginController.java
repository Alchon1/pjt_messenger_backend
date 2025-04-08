package org.zerock.myapp.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.zerock.myapp.service.LoginServiceImpl;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/")
public class LoginController {

	private final LoginServiceImpl loginService = new LoginServiceImpl();
	
	
	@PostMapping("/login")
	public String login() {
		return "redirect:/list"; 
	}
	
	 @PostMapping("/register")
	    public String processLogin (
	            @RequestParam("username") String username,
	            @RequestParam("password") String password
	    ) {
	        userService.saveUser(username, password); // DB에 저장
	        return "redirect:/api/auth/login"; // 로그인 후 홈으로 이동
	    }

	
	
	
	
	
	@GetMapping("/login") // 타임리프로 사용 시 활성화
	public String loginPage() {
		return "login";
	}
	
	@GetMapping("/register") // 타임리프로 사용 시 활성화
	public String registerPage() {
		return "register";
	}
	
}  // end class
