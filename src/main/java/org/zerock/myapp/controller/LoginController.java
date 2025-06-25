package org.zerock.myapp.controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.zerock.myapp.domain.EmployeeDTO;
import org.zerock.myapp.domain.TokenResponseDto;
import org.zerock.myapp.entity.Employee;
import org.zerock.myapp.secutity.JwtProvider;
import org.zerock.myapp.service.LoginServiceImpl;
import org.zerock.myapp.util.RoleUtil;

import lombok.RequiredArgsConstructor;

@RequestMapping("/auth")
@RestController
@RequiredArgsConstructor
public class LoginController {

	
	private final LoginServiceImpl service;
	private final JwtProvider jwt;
	private final AuthenticationManager AuthManager;
	
	
	
    @PostMapping("/login")
    public ResponseEntity<?> login(@ModelAttribute EmployeeDTO dto) {
        try {
            Optional<Employee> employeeOpt = service.login(dto.getLoginId(), dto.getPassword());
            if (employeeOpt.isEmpty()) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                                     .body(Map.of("error", "로그인 실패. 인증된 사용자가 아닙니다."));
            }
            Employee employee = employeeOpt.get();

            // Spring Security Context 설정 (선택)
            UsernamePasswordAuthenticationToken authToken =
                new UsernamePasswordAuthenticationToken(
                    employee.getLoginId(),
                    null,
                    getAuthorities(employee)
                );
            SecurityContextHolder.getContext().setAuthentication(authToken);

            // JWT 토큰 발급
            String token        = jwt.generateToken(employee.getLoginId(), employee);
            String refreshToken = jwt.generateRefreshToken(employee.getLoginId());
            long   expiresAt    = System.currentTimeMillis() + 3600 * 1000; // 1시간

            TokenResponseDto response =
                new TokenResponseDto(token, expiresAt, refreshToken);
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                                 .body(Map.of("error", e.getMessage()));
        }
    }
	
	private List<GrantedAuthority> getAuthorities(Employee emp) {
	    String roleName = RoleUtil.mapPositionToRole(emp.getPosition());   // 예: "DepartmentLeader"
	    return List.of(new SimpleGrantedAuthority("ROLE_" + roleName));    // 예: "ROLE_DepartmentLeader"
	}
	
 	@PostMapping("/refresh")
	public ResponseEntity<?> refresh(@RequestBody Map<String, String> body) {
 		String beforeRefresh = body.get("refreshToken");
 		
 		try {
 			String subject = jwt.verifyRefreshToken(beforeRefresh);
 			
 			Employee employee = service.findByLoginId(subject)
 		            .orElseThrow(() -> new UsernameNotFoundException("User not found: " + subject));
 			String newAccessToken = jwt.generateToken(subject, employee);
 			String newRefreshToken = jwt.generateRefreshToken(subject);
 			
 			TokenResponseDto response = new TokenResponseDto (
 					newAccessToken,
 					System.currentTimeMillis() + 3600 * 1000,
 					newRefreshToken
 					);
 				return ResponseEntity.ok(response);
 			
 		} catch(Exception e) {
 			return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "Invalid refresh token"));
 		}
 	}
	
	
	
	
	@GetMapping("/myinfo")
	public ResponseEntity<?> getMyInfo(Authentication authentication) {
		String loginId = authentication.getName();
		return ResponseEntity.ok("반가워요. " + loginId + "님 !");
	}
	

	
}

