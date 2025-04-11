package org.zerock.myapp.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.zerock.myapp.domain.TokenResponseDto;
import org.zerock.myapp.service.JwtProvider;

/**
 * 인증 관련 요청을 처리하는 컨트롤러
 */
@RestController
@RequestMapping("/api/auth")
public class TokenController {

    /**
     * 로그인 요청 처리
     * @param username 사용자 이름 (실제로는 인증 로직이 필요)
     * @return JWT 토큰 및 만료 시간 정보
     */
    @PostMapping("/login")
    public ResponseEntity<TokenResponseDto> login(@RequestParam("username") String username) {
        // 실제 애플리케이션에서는 사용자 인증 로직을 추가해야 합니다.
        String token = JwtProvider.generateToken(username, null); // JWT 토큰 생성
        long expiresAt = System.currentTimeMillis() + 3600 * 1000; // 1시간 후 만료 (밀리초 단위)

        // 클라이언트에 전달할 응답 객체 생성
        TokenResponseDto response = new TokenResponseDto(token, expiresAt);

        return ResponseEntity.ok(response); // 응답 반환
    }
}
