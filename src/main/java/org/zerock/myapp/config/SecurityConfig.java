package org.zerock.myapp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
//@EnableWebSecurity //커스텀 화면 구성시
public class SecurityConfig {
    
	
	@Bean
    public PasswordEncoder passwordEncoder() {return new BCryptPasswordEncoder();}
    
    
    
    /*
    //@Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())  // CSRF 비활성화 (프론트엔드 연동 시 필요)
            .cors(cors -> cors.disable())  // CORS 정책 조정 (필요하면 설정)
            .authorizeHttpRequests(auth -> auth
            		.requestMatchers("/api/public/**" , "/project/**","/", "/index.html", "/static/**", "/assets/**" , "/**").permitAll()  // 공개 API 허용
                .requestMatchers("/api/user/**").authenticated() // 인증 필요한 API
            )
            .formLogin(login -> login.disable())  // 기본 로그인 페이지 비활성화
            .httpBasic(httpBasic -> httpBasic.disable());  // 기본 인증 비활성화 (JWT 사용시)

        return http.build();
    }
    
    
//    @Bean // CORS 설정.
//    public CorsFilter corsFilter() {
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        CorsConfiguration config = new CorsConfiguration();
//        
//        config.setAllowCredentials(true);
//        config.setAllowedOrigins(List.of("http://localhost:3000")); // React 프론트엔드 주소
//        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
//        config.setAllowedHeaders(List.of("*"));
//        
//        source.registerCorsConfiguration("/**", config);
//        return new CorsFilter(source);
//    }
    
    */
}
