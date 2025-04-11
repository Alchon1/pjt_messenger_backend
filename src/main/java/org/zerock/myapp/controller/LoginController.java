package org.zerock.myapp.controller;

import java.util.Map;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.zerock.myapp.domain.EmployeeDTO;
import org.zerock.myapp.domain.TokenResponseDto;
import org.zerock.myapp.entity.Employee;
import org.zerock.myapp.service.JwtProvider;
import org.zerock.myapp.service.LoginServiceImpl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class LoginController {

	private final LoginServiceImpl loginService;

	
	/**
	 * 로그인 요청 처리
	 * @param username 사용자 이름 (실제로는 인증 로직이 필요)
	 * @return JWT 토큰 및 만료 시간 정보
	 */
	
	@PostMapping("/login")
	public ResponseEntity<?> login( // ResponseEntity<?> :Spring 에서 http 응답 전체를 커스터마이징 하고 싶을때 사용하는 클래
	        @RequestParam("username") String username,
	        @RequestParam("password") String password
	) {
	    Optional<Employee> employeecheck = loginService.login(username, password);

	    if (employeecheck.isEmpty()) {
	        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인 실패");
	    }

	    Employee employee = employeecheck.get();
	    String token = JwtProvider.generateToken(username, employee);  // 여기서 생성
	    long expiresAt = System.currentTimeMillis() + 3600 * 1000; // 1시간 후 만

	    TokenResponseDto response = new TokenResponseDto(token, expiresAt);
	    return ResponseEntity.ok(response);  // 토큰 클라이언트에 반환
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
