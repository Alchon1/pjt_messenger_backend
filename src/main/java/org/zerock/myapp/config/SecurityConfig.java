package org.zerock.myapp.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.zerock.myapp.secutity.JwtAuthenticationFilter;
import org.zerock.myapp.secutity.JwtProvider;
import org.zerock.myapp.secutity.LoginSuccessUrlHandler;

import jakarta.servlet.http.HttpServletResponse;


@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private LoginSuccessUrlHandler loginSuccessUrlHandler;

    @Bean
    public BCryptPasswordEncoder BcryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration cfg = new CorsConfiguration();
        cfg.setAllowedOrigins(List.of("http://localhost:3000"));
        cfg.setAllowedMethods(List.of("GET","POST","PUT","DELETE","OPTIONS"));
        // Authorization 헤더를 반드시 허용!
        cfg.setAllowedHeaders(List.of("Authorization","Content-Type","X-Requested-With"));
        cfg.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", cfg);
        return source;
    }
    
    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration authenticationConfiguration
    ) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
    
    
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, JwtProvider jwtProvider) throws Exception {
        return http
                .csrf().disable()
                .cors().configurationSource(corsConfigurationSource())
                .and()
                
//                .authorizeHttpRequests(auth -> auth
//                        .anyRequest().permitAll()
//                ) // 테스트 용.
                
//             // 권한·인증 예외 처리
//                .exceptionHandling(exception -> exception
//                    // 인증 실패(로그인 안 된 상태) 시
//                    .authenticationEntryPoint((request, response, authException) -> {
//                        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//                        response.setContentType("application/json; charset=UTF-8");
//                        response.getWriter().write("{\"message\":\"권한이 없습니다. 로그인 후 다시 시도해주세요.\"}");
//                    })
//                    // 인증은 됐으나 권한이 없을 때
//                    .accessDeniedHandler((request, response, accessDeniedException) -> {
//                        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
//                        response.setContentType("application/json; charset=UTF-8");
//                        response.getWriter().write("{\"message\":\"권한이 없습니다.\"}");
//                    })
//                )
                
                // 4) 폼 로그인 & 성공 핸들러 연결
//                .formLogin(form -> form
//                    .loginProcessingUrl("/auth/login")           // React(또는 클라이언트)에서 POST 할 로그인 URL
//                    .successHandler(loginSuccessUrlHandler)      // 로그인 성공 시 getRedirectUrlByRole() 호출
//                    .failureHandler((req, res, ex) -> {
//                        res.sendError(HttpServletResponse.SC_UNAUTHORIZED);
//                    })
//                    .permitAll()
//                )
            
                .authorizeHttpRequests(auth -> auth
                	    
                		  //SYSTEM_MANAGER는 모든 요청 허용
//                	    .requestMatchers("/**").hasRole("SystemManager")
                		
                	    .requestMatchers(
                	        "/auth/login",
                	        "/employee/{empno}",
                	        "/board/Notice",
                	        "/board/Feedback/register",
                	        "/project/status",
                	        "/employee",
                	        "/employee/**",
                	        "/board/notice/list",
                	        "/file/upload"
                	    ).permitAll()
                	    .requestMatchers(HttpMethod.GET, "/board/notice/{id}").permitAll()
                	    .requestMatchers(HttpMethod.GET, "/board/Feedback/{id}").permitAll()
                	    .requestMatchers(HttpMethod.PUT, "/board/Feedback/{id}").permitAll()
                	    .requestMatchers(HttpMethod.DELETE, "/board/Feedback/{id}").permitAll()
                	    .requestMatchers(HttpMethod.GET, "/department/**").permitAll()

                	    // 인사담당자.
//                	    .requestMatchers(
//                	        "/employee",
//                	        "/employee/**"
//                	    ).hasRole("HireManager")

                	    // 프로젝트.
                	    .requestMatchers(
                	        "/project",
                	        "/project/**",
                	        "/project/upComing"
                	    ).hasAnyRole("DepartmentLeader", "TeamLeader" ,"SystemManager" , "CEO")
                	    .requestMatchers(HttpMethod.GET, "/project/{id}").hasAnyRole("DepartmentLeader", "TeamLeader" , "SystemManager" , "CEO")
                	    .requestMatchers(HttpMethod.DELETE, "/work/{id}").hasAnyRole("DepartmentLeader", "TeamLeader" , "SystemManager" , "CEO")

                	    // 업무.
                	    .requestMatchers(
                	        "/work",
                	        "/work/**"
                	    ).hasAnyRole("Employee", "TeamLeader", "DepartmentLeader" , "SystemManager")
                	    .requestMatchers(HttpMethod.GET, "/work/{id}").hasAnyRole("Employee", "TeamLeader", "DepartmentLeader", "SystemManager")
                	    .requestMatchers(HttpMethod.PUT, "/work/{id}").hasAnyRole("Employee", "TeamLeader", "DepartmentLeader", "SystemManager")
                	    .requestMatchers(HttpMethod.DELETE, "/work/{id}").hasAnyRole("Employee", "TeamLeader", "DepartmentLeader", "SystemManager")

                	    // 게시판.
                	    .requestMatchers(HttpMethod.GET, "/board/Notice/register").hasAnyRole("Employee", "TeamLeader","DepartmentLeader","CEO", "SystemManager")
                	    .requestMatchers(HttpMethod.PUT, "/board/Notice/{id}").hasAnyRole("Employee", "TeamLeader","DepartmentLeader","CEO", "SystemManager")
                	    .requestMatchers(HttpMethod.DELETE, "/board/Notice/{id}").hasAnyRole( "TeamLeader","DepartmentLeader","CEO", "SystemManager")

                	    // 채팅.
                	    .requestMatchers(HttpMethod.POST, "/chat").hasAnyRole("Employee", "TeamLeader", "DepartmentLeader", "CEO", "SystemManager")
                	    .requestMatchers(HttpMethod.GET, "/chat/{id}").hasAnyRole("Employee", "TeamLeader", "DepartmentLeader", "CEO", "SystemManager")
                	    .requestMatchers(HttpMethod.PUT, "/chat/{id}").hasAnyRole("Employee", "TeamLeader", "DepartmentLeader", "CEO", "SystemManager")
                	    .requestMatchers(HttpMethod.GET, "/list/{empno}").hasAnyRole("Employee", "TeamLeader", "DepartmentLeader", "CEO", "SystemManager")
                	    .requestMatchers(HttpMethod.DELETE, "/chat/{id}").hasAnyRole("Employee", "TeamLeader", "DepartmentLeader", "CEO", "SystemManager")
                	    .requestMatchers(HttpMethod.GET, "/message").hasAnyRole("Employee", "TeamLeader", "DepartmentLeader", "CEO", "SystemManager")
                	    .requestMatchers(HttpMethod.POST, "/message/{id}/summarize").hasAnyRole("Employee", "TeamLeader", "DepartmentLeader", "CEO", "SystemManager")

                	    // CEO
                	    .requestMatchers(HttpMethod.GET, "/board/Feedback").hasAnyRole("CEO", "SystemManager")

                	   
                	    
                	    
//                	    .anyRequest().authenticated()
                	    .anyRequest().permitAll()
                	)

//                .sessionManagement()
//                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                .and()
                .addFilterBefore(new JwtAuthenticationFilter(jwtProvider), UsernamePasswordAuthenticationFilter.class)
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessHandler((request, response, authentication) -> {
                            response.setStatus(HttpServletResponse.SC_OK);
                        })
                )
                .build(); 
    }
}

