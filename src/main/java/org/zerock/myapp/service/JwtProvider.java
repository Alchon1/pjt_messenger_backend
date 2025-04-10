package org.zerock.myapp.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;

import java.util.Date;

import org.zerock.myapp.entity.Employee;
import org.zerock.myapp.util.RoleUtil;

/**
 * JWT 토큰의 생성 및 검증 기능을 담당하는 유틸리티 클래스
 */
public class JwtProvider {

    // 비밀키: 실제 서비스에서는 환경 변수나 외부 설정을 통해 관리할 것
    private static final String SECRET_KEY = "mySuperSecretKey12345";

    // HMAC256 알고리즘 선택 (SHA-256 해시 함수를 기반으로 한 HMAC 방식)
    private static final Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY);

    /**
     * 토큰 생성
     * @param subject 사용자 식별자 (ex. username)
     * @return JWT 토큰 문자열
     */
    public static String generateToken(String subject) {
        Date now = new Date();
        Date expiresAt = new Date(now.getTime() + 3600 * 1000); // 1시간 후 만료

        String role = RoleUtil.mapPositionToRole();
        
        return 
        		String token = JWT.create()
                .withSubject(subject) // 사용자 식별자 설정
                .withIssuer("yourAppName") // 토큰 발급자 정보
                .withIssuedAt(now) // 발급 시각
                .withExpiresAt(expiresAt) // 만료 시각
                .withClaim("role", role) // 추가 클레임 설정 (예: 권한 정보)
                .sign(algorithm); // 알고리즘과 비밀키로 서명
        		
    }

    /**
     * 토큰 검증
     * @param token JWT 토큰
     * @return subject (사용자 식별자), 유효하지 않으면 예외 발생
     */
    public static String verifyToken(String token) {
        // 토큰 서명을 검증할 검증기 생성
        JWTVerifier verifier = JWT.require(algorithm)
                                  .withIssuer("yourAppName") // 발급자 일치 여부 확인
                                  .build();

        // 토큰 검증 및 디코딩
        DecodedJWT decoded = verifier.verify(token);
        return decoded.getSubject(); // 사용자 식별자 반환
    }
}
