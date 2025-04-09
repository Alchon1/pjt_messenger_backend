package org.zerock.myapp.controller;

import java.util.Optional;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.zerock.myapp.domain.EmployeeDTO;
import org.zerock.myapp.entity.Employee;
import org.zerock.myapp.service.LoginServiceImpl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class LoginController {

	private final LoginServiceImpl loginService;
	
	
	@PostMapping("/login")
	public String login(
            @RequestParam("username") String username,
            @RequestParam("password") String password
            ) {
			Optional<Employee> employee = loginService.login(username, password);
			
		return "redirect:/list"; 
	}
	
	 @PostMapping("/register")
	    public String register (
	            @RequestParam("username") String username,
	            @RequestParam("password") String password
	    ) {
	       EmployeeDTO dto = new EmployeeDTO();
	       dto.setLoginId(username);
	       dto.setPassword(password);
	       loginService.registerEmployee(dto);
	       return "redirect:/auth/login";
	    }


	
//	@GetMapping("/login") // 타임리프로 사용 시 활성화
//	public String loginPage() {
//		return "login";
//	}
//	
//	@GetMapping("/register") // 타임리프로 사용 시 활성화
//	public String registerPage() {
//		return "register";
//	}
	
}  // end class
