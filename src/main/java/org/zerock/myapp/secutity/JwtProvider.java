package org.zerock.myapp.secutity;

import java.time.Duration;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.zerock.myapp.entity.Employee;
import org.zerock.myapp.util.RoleUtil;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;

import lombok.RequiredArgsConstructor;

/**
 * JWT 토큰의 생성 및 검증 기능을 담당하는 유틸리티 클래스
 */


@Service
@RequiredArgsConstructor
public class JwtProvider {

    // 비밀키: 실제 서비스에서는 환경 변수나 외부 설정을 통해 관리할 것
    private static final String SECRET_KEY = "mySuperSecretKey12345";

    // HMAC256 알고리즘 선택 (SHA-256 해시 함수를 기반으로 한 HMAC 방식)
    private static final Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY);

    // Redis 주입.
    private final StringRedisTemplate redisTemplate;
    
    
    /**
     * 토큰 생성
     * @param subject 사용자 식별자 (ex. username)
     * @return JWT 토큰 문자열
     */
    public String generateToken(String subject , Employee employee) {
        Date now = new Date();
        Date expiresAt = new Date(now.getTime() + 3600 * 1000); // 24시간 후 만료

        int position = employee.getPosition(); // 혹은 적절한 값을 할당
        String roles = RoleUtil.mapPositionToRole(position);
        
        
        		String token = JWT.create()
                .withSubject(subject) // 사용자 식별자 설정
                .withIssuer("Mark") // 토큰 발급자 정보
                .withIssuedAt(now) // 발급 시각
                .withExpiresAt(expiresAt) // 만료 시각
                .withClaim("roles", List.of(roles)) // 추가 클레임 설정 (예: 권한 정보)z
//               .withClaim("roles", List.of("ROLE_HireManager", "ROLE_User")) : 여러 개의 권한 설정시.
                .withClaim("empno", employee.getEmpno())
                .withClaim("name", employee.getName())
                .withClaim("email", employee.getEmail())
                .withClaim("loginId", employee.getLoginId())
                .withClaim("position", position)
                .withClaim("department", employee.getDepartment().getId())
                .sign(algorithm); // 알고리즘과 비밀키로 서명
        		
        		
        	return token;
    }
    
    
    // refreshToken 
    public  String generateRefreshToken (String subject) {
    	Date now = new Date();
    	Date expiresAt = new Date(now.getTime() +  10L * 24 * 3600 * 1000 ); // 10 일 뒤 만료.
    		
    		String jti = UUID.randomUUID().toString();
    		String refreshToken = JWT.create()
    				.withSubject(subject)
    				.withIssuer("Mark")
    				.withIssuedAt(now)
    				.withExpiresAt(expiresAt)
    				.withJWTId(jti)
    				.sign(algorithm);
    		
    		// redis 에 저장.
    		redisTemplate.opsForValue().set(
    				"refresh:" + jti,
    				refreshToken,
    				Duration.ofDays(10)
    				);
    		
    		return refreshToken;
    }
    
    
    

    /**
     * Access Token토큰 검증
     * @param token JWT 토큰
     * @return subject (사용자 식별자), 유효하지 않으면 예외 발생
     */
    public String verifyToken(String token) {
        // 토큰 서명을 검증할 검증기 생성
        JWTVerifier verifier = JWT.require(algorithm)
                                  .withIssuer("Mark") // 발급자 일치 여부 확인
                                  .build();

        // Access 토큰 검증 및 디코딩
        DecodedJWT decoded = verifier.verify(token);
        return decoded.getSubject(); // 사용자 식별자 반환
    }
   
    
    
    /**
     * refreshToken 검증 및 삭제. 
     * @param refreshToken
     * @return
     */
    
    public String verifyRefreshToken(String refreshToken) {
    	
    	JWTVerifier verifier = JWT
    			.require(algorithm)
    			.withIssuer("Mark")
    			.build();
    	
    	DecodedJWT decoded = verifier.verify(refreshToken);
    	String jti = decoded.getId();
    	redisTemplate.delete("refresh:" + jti); // 이전 refreshToken 삭제.
    	
    	return decoded.getSubject();
    }
    


    
    
    
    
}
