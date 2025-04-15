package org.zerock.myapp.service;

import java.util.Date;

import org.springframework.stereotype.Service;
import org.zerock.myapp.entity.Employee;
import org.zerock.myapp.util.RoleUtil;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;

/**
 * JWT 토큰의 생성 및 검증 기능을 담당하는 유틸리티 클래스
 */

@Service
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
    public static String generateToken(String subject , Employee employee) {
        Date now = new Date();
        Date expiresAt = new Date(now.getTime() + 3600 * 1000); // 1시간 후 만료

        int position = employee.getPosition(); // 혹은 적절한 값을 할당
        String role = RoleUtil.mapPositionToRole(position);
        
        
        		String token = JWT.create()
                .withSubject(subject) // 사용자 식별자 설정
<<<<<<< Updated upstream
                .withIssuer("yourAppName") // 토큰 발급자 정보
                .withIssuedAt(now) // 발급 시각
                .withExpiresAt(expiresAt) // 만료 시각
                .withClaim("role", role) // 추가 클레임 설정 (예: 권한 정보)
=======
                .withIssuer("Mark") // 토큰 발급자 정보
                .withIssuedAt(now) // 발급 시각
                .withExpiresAt(expiresAt) // 만료 시각
                .withClaim("role", role) // 추가 클레임 설정 (예: 권한 정보)
                .withClaim("name", employee.getName())
>>>>>>> Stashed changes
                .sign(algorithm); // 알고리즘과 비밀키로 서명
        		
        	return token;
    }

    /**
     * 토큰 검증
     * @param token JWT 토큰
     * @return subject (사용자 식별자), 유효하지 않으면 예외 발생
     */
    public static String verifyToken(String token) {
        // 토큰 서명을 검증할 검증기 생성
        JWTVerifier verifier = JWT.require(algorithm)
<<<<<<< Updated upstream
                                  .withIssuer("yourAppName") // 발급자 일치 여부 확인
=======
                                  .withIssuer("Mark") // 발급자 일치 여부 확인
>>>>>>> Stashed changes
                                  .build();

        // 토큰 검증 및 디코딩
        DecodedJWT decoded = verifier.verify(token);
        return decoded.getSubject(); // 사용자 식별자 반환
    }
<<<<<<< Updated upstream
=======
    
    // 토큰 안에 있는 정보 사용 ( 백엔드용 )
//    public static DecodedJWT decodeToken(String token) {
//    	JWTVerifier verifier = JWT.require(algorithm).withIssuer("Mark").build();
//    	return verifier.verify(token);  // 검증 + 디코딩
//    }
//    DecodedJWT decoded = JWT.require(algorithm).build().verify(token);
//    String loginId = decoded.getSubject();
//    String name = decoded.getClaim("name").asString(); 
//    String role = decoded.getClaim("role").asString();
    

    
    


    
    
    
    
>>>>>>> Stashed changes
}
