package org.zerock.myapp.config;

import java.io.IOException;
import java.util.List;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.zerock.myapp.controller.common.JwtPrincipal;
import org.zerock.myapp.service.JwtProvider;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtProvider jwtProvider;

    public JwtAuthenticationFilter(JwtProvider jwtProvider) {
        this.jwtProvider = jwtProvider;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = authHeader.substring(7); // "Bearer " 제거

        try {
            // 1. JWT 검증 + 사용자 추출
            String username = jwtProvider.verifyToken(token);

            // 2. JWT에서 roles 추출
            DecodedJWT decodedJWT = JWT.require(Algorithm.HMAC256("mySuperSecretKey12345"))
                    .withIssuer("Mark") // ❗ issuer도 검증해야 함
                    .build()
                    .verify(token);

            // JWT 토큰에서 꺼낼 정보 기입.
            String role = decodedJWT.getClaim("roles").asString();
            String empno = decodedJWT.getClaim("empno").asString();
            

            if (role == null || role.isEmpty()) {
                System.out.println("JWT에 roles 정보 없음");
                filterChain.doFilter(request, response);
                return;
            }

            // 3. SecurityContext에 권한 주입
            
            JwtPrincipal principal = new JwtPrincipal(empno, username, role);            
            SimpleGrantedAuthority authority = new SimpleGrantedAuthority(role);
            Authentication auth = new UsernamePasswordAuthenticationToken(principal, null, List.of(authority));
            SecurityContextHolder.getContext().setAuthentication(auth);

           
            System.out.println("JWT 필터 통과: " + username + ", 권한: " + role);

        } catch (Exception e) {
            System.out.println("JWT 검증 실패: " + e.getMessage());
        }

        filterChain.doFilter(request, response);
    }
}
