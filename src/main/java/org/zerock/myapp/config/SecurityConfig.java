package org.zerock.myapp.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.zerock.myapp.service.JwtProvider;

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
    public SecurityFilterChain filterChain(HttpSecurity http, JwtProvider jwtProvider) throws Exception {
        return http
                .csrf().disable()
//                .cors()
//                .and()
                

                
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().permitAll()
                )
                
//                .authorizeHttpRequests(auth -> auth
//                        // 로그인, 회원가입
//                        .requestMatchers("/auth/login", "/employee/{empno}").permitAll()
//                        .requestMatchers("/employee", "/employee/**").hasRole("HireManager")
//                        .anyRequest().permitAll()
//                )
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
                .build(); // ← 이게 최종 마무리
    }
}
