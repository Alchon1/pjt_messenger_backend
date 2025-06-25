package org.zerock.myapp.domain;

import lombok.Data;

/**
 * 클라이언트에 JWT 토큰과 만료시간을 전달하기 위한 DTO
 */

@Data
public class TokenResponseDto {
    private String token;      // JWT 토큰 문자열
    private long expiresAt;    // 토큰 만료 시각 (Unix timestamp, 밀리초 단위)
    private String refreshToken;

    // 생성자
    public TokenResponseDto(String token, long expiresAt, String refreshToken) {
        this.token = token;
        this.expiresAt = expiresAt;
        this.refreshToken = refreshToken;
    }

    // Getter 메서드
    public String getToken() {
        return token;
    }

    public long getExpiresAt() {
        return expiresAt;
    }
    
    // Getter 메서드
    public String getRefreshToken() {
        return refreshToken;
    }
}
