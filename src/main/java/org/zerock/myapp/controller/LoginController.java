package org.zerock.myapp.controller;

<<<<<<< Updated upstream
import java.util.Map;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.zerock.myapp.domain.EmployeeDTO;
=======
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
>>>>>>> Stashed changes
import org.zerock.myapp.domain.TokenResponseDto;
import org.zerock.myapp.entity.Employee;
import org.zerock.myapp.service.JwtProvider;
import org.zerock.myapp.service.LoginServiceImpl;

<<<<<<< Updated upstream
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
//@RestController
@Controller
@RequestMapping("/auth")
@RequiredArgsConstructor
public class LoginController {

	private final LoginServiceImpl loginService;

	
	/**
	 * 로그인 요청 처리
	 * @param username 사용자 이름 (실제로는 인증 로직이 필요)
	 * @return JWT 토큰 및 만료 시간 정보
	 */
	
	@GetMapping("/login") // 타임리프로 사용 시 활성화
	public String loginPage() {
		return "login";
	}
	
	
	@PostMapping("/login")
	@ResponseBody
	public ResponseEntity<?> login( // ResponseEntity<?> :Spring 에서 http 응답 전체를 커스터마이징 하고 싶을때 사용하는 클래
	        @RequestParam("loginId") String loginId,
	        @RequestParam("password") String password
	) {
	    Optional<Employee> employeecheck = loginService.login(loginId, password);
=======
@RequestMapping("/auth")
@RestController
public class LoginController {

	@Autowired LoginServiceImpl service;
	
	@PostMapping("/login")
	ResponseEntity<?> login( // ResponseEntity<?> :Spring 에서 http 응답 전체를 커스터마이징 하고 싶을때 사용하는 클래
	        @RequestParam("loginId") String loginId,
	        @RequestParam("password") String password
	) {
	
	try {
	    Optional<Employee> employeecheck = service.login(loginId, password);
>>>>>>> Stashed changes

	    if (employeecheck.isEmpty()) {
	        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인 실패. 인증된 사용자가 아닙니다.");
	    }

	    Employee employee = employeecheck.get();
	    String token = JwtProvider.generateToken(loginId, employee);  // 여기서 생성
<<<<<<< Updated upstream
	    long expiresAt = System.currentTimeMillis() + 3600 * 1000; // 1시간 후 만

	    TokenResponseDto response = new TokenResponseDto(token, expiresAt);
	    return ResponseEntity.ok(response);  // 토큰 클라이언트에 반환
	} 


	
}  // end class
=======
	    long expiresAt = System.currentTimeMillis() + 3600 * 1000; // 1시간 후 만료.

	    TokenResponseDto response = new TokenResponseDto(token, expiresAt);
	    return ResponseEntity.ok(response);  // 토큰 클라이언트에 반환
	} catch (IllegalArgumentException e) {
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
	}
	
	} // end login
	
	@GetMapping("/myinfo")
	public ResponseEntity<?> getMyInfo(Authentication authentication) {
		String loginId = authentication.getName();
		return ResponseEntity.ok("반가워요. " + loginId + "님 !");
	}
	
	
	
}
>>>>>>> Stashed changes
