package org.zerock.myapp.config;

import java.io.IOException;
import java.util.List;

<<<<<<< Updated upstream
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
=======
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
>>>>>>> Stashed changes
import org.springframework.web.filter.OncePerRequestFilter;
import org.zerock.myapp.service.JwtProvider;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * JwtAuthenticationFilter 를 쓰는 이유
 * 
 *  JwtProvider 라는 파일을 만들어서 토큰을 생산하고 검증을 하고 있으나 현재 Spring Security 의 인증 필터 체인을 
 *  사용함으로 이 과정 중 JWT 인증을 끼워넣으려면 요청이 들어올때 "Authorization 헤더에 있는 JWT를 자동으로 꺼내서 검증 해주는 필터"가
 *  하나 필요하다. 그것이 바로 JwtAuthenticationFilter 인것 !
 */

<<<<<<< Updated upstream

public class JwtAuthenticationFilter extends OncePerRequestFilter {
	
	private final JwtProvider jwtProvider;
	
	public JwtAuthenticationFilter (JwtProvider jwtProvider) {
		this.jwtProvider = jwtProvider;
	}
	
	@Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7); // "Bearer " 제거

            try {
                String username = jwtProvider.verifyToken(token);  // 검증 성공 시 사용자 정보 추출

                // 여기서 SecurityContext에 인증 객체 저장
                UsernamePasswordAuthenticationToken auth =
                        new UsernamePasswordAuthenticationToken(username, null, List.of());

                SecurityContextHolder.getContext().setAuthentication(auth);

            } catch (Exception e) {
                // 유효하지 않은 토큰 → 인증 실패 → 아무것도 설정 안 함
            }
        }

        // 다음 필터로 넘김
        filterChain.doFilter(request, response);
    }
=======
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter { // OncePerRequestFilter : 한 요청당 한번만 실행되는 필
	
	@Autowired JwtProvider jwtProvider;
	
	public JwtAuthenticationFilter (JwtProvider jwtProvider) {
		this.jwtProvider = jwtProvider; // jwt를 필터체인에서 자동으로 꺼내고 검증하는 역할.
	}
	
	@Override
	protected void doFilterInternal(HttpServletRequest request,
	                                HttpServletResponse response,
	                                FilterChain filterChain) throws ServletException, IOException {

	    String authHeader = request.getHeader("Authorization"); // Authorization 헤더에서 jwt 꺼내는 역할.

	    // 토큰이 없으면 그냥 넘긴다 (허용된 요청 처리 목적)
	    if (authHeader == null || !authHeader.startsWith("Bearer ")) {
	        filterChain.doFilter(request, response);
	        return;
	    }

	    String token = authHeader.substring(7); // "Bearer " 제거

	    try {
	        String username = jwtProvider.verifyToken(token);  // 검증 성공 시 사용자 정보 추출

	        // 여기서 SecurityContext에 인증 객체 저장
	        UsernamePasswordAuthenticationToken auth =
	                new UsernamePasswordAuthenticationToken(username, null, List.of());

	        SecurityContextHolder.getContext().setAuthentication(auth);

	    } catch (Exception e) {
	        // 토큰 검증 실패 시에도 요청 자체는 넘기되, 인증된 사용자로 처리하진 않음
	        System.out.println("JWT 검증 실패: " + e.getMessage());
	    }

	    // 다음 필터로 넘김 (딱 한 번만!)
	    filterChain.doFilter(request, response);
	}

>>>>>>> Stashed changes
	
	
} // end class



