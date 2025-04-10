package org.zerock.myapp.domain;

/**
 * 클라이언트에 JWT 토큰과 만료시간을 전달하기 위한 DTO
 */
public class TokenResponseDto {
    private String token;      // JWT 토큰 문자열
    private long expiresAt;    // 토큰 만료 시각 (Unix timestamp, 밀리초 단위)

    // 생성자
    public TokenResponseDto(String token, long expiresAt) {
        this.token = token;
        this.expiresAt = expiresAt;
    }

    // Getter 메서드
    public String getToken() {
        return token;
    }

    public long getExpiresAt() {
        return expiresAt;
    }
}
