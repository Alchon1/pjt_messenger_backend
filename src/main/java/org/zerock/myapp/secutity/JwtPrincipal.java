package org.zerock.myapp.secutity;

import java.io.Serial;
import java.util.Collection;
import java.util.List;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import lombok.Data;

@Data
public class JwtPrincipal implements UserDetails {
	@Serial
	private static final long serialVersionUID = 1L;

	private final String empno;
	private final String role;
	private final String name;
	


	// 🔐 권한 정보 반환 (Spring Security 필수)
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {

		return List.of(new SimpleGrantedAuthority(role));
	}
	

	// 계정 유효성 검증 메소드 들...
	@Override
	public boolean isAccountNonExpired() {
		// 계정이 만료되지 않았는지 여부를 반환
		// true : 정상 / false : 만료계정
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// 계정이 잠겨 있지 않은지 여부를 반환
		// true : 정상 / false : Lock, 로그인 불가
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// 사용자의 인증 정보(주로 비밀번호)가 만료되지 않았는지 여부를 반환
		// true : 정상
		return true;
	}

	@Override
	public boolean isEnabled() {
		// 사용자가 활성화되어 있는지 여부를 반환
		// true : 정상
		return true;
	}
	

	// ✅ 사용자 ID 기준 (Spring이 여기에 username 기반 검증함)
	@Override
	public String getUsername() {  // 사용자의 고유 식별자(예: empno, loginId 등)를 반환
		return this.empno; // 또는 loginId
	}


	@Override
	public String getPassword() {
		return null;
	}



}

// 사용 예제

//@GetMapping("/me")
//public ResponseEntity<?> getMyInfo(@AuthenticationPrincipal JwtPrincipal principal) {
//    return ResponseEntity.ok("사번: " + principal.getEmpno() +
//                             ", 이름: " + principal.getUsername() +
//                             ", 권한: " + principal.getRole());
//}

// 하나만 꺼낼때.
//@GetMapping("/empno")
//public ResponseEntity<String> getEmpno(@AuthenticationPrincipal JwtPrincipal principal) {
//    return ResponseEntity.ok(principal.getEmpno());
//}

//@Service
//public class YourService {
//    
//    public List<YourDto> listSomething(...) {
//        JwtPrincipal principal = 
//            (JwtPrincipal) SecurityContextHolder
//                .getContext()
//                .getAuthentication()
//                .getPrincipal();
//        
//        String empno = principal.getEmpno();
//        // empno 이용한 비즈니스 로직...
//    }
//}



