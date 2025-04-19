package org.zerock.myapp.controller.common;

import lombok.Data;

@Data
public class JwtPrincipal {
	private final String empno;
    private final String username;
    private final String role;
	
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