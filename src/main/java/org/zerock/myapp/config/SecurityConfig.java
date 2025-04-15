package org.zerock.myapp.config;
<<<<<<< Updated upstream
=======

>>>>>>> Stashed changes
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.zerock.myapp.service.JwtProvider;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
<<<<<<< Updated upstream
	
	@Autowired
	private LoginSuccessUrlHandler loginSuccessUrlHandler;

=======

    @Autowired
    private LoginSuccessUrlHandler loginSuccessUrlHandler;

>>>>>>> Stashed changes
    @Bean
    public BCryptPasswordEncoder BcryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

<<<<<<< Updated upstream
//    @Bean
//    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
//        return config.getAuthenticationManager();
//    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, JwtProvider jwtProvider) throws Exception {
        return http
                .csrf().disable() // csrf 공격을 방지하기 위해.
                .authorizeHttpRequests(auth -> auth
                		
                		// 모든 사람이 접근 가능. 
                        .requestMatchers(
                        		"/auth/login",
                        		"/login",
                        		"/css/**",
        						"/js/**",
        						"/img/**").permitAll()
                        
                        
//                        .requestMatchers("//admin/**").hasAnyRole(
//                        		"Employee", "TeamLeader", "DepartmentLeader",
//                        		"CEO", "HireManager", "SystemManager" , "user")
////                        .anyRequest().authenticated() / 인증된 사용자만 사용가능.
//                        .anyRequest().permitAll()
                )
                .sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilterBefore(new JwtAuthenticationFilter(jwtProvider), UsernamePasswordAuthenticationFilter.class)
    			.formLogin(form -> form
				.loginPage("/auth/login") // 타임리프.
				.loginProcessingUrl("/login") // PostMapping controller 
				.successHandler(loginSuccessUrlHandler)
//				.defaultSuccessUrl("/")
				.failureUrl("/auth/login?error=true")
				.permitAll()
				)
		.logout(logout -> logout
				.logoutUrl("/logout")
				.logoutSuccessUrl("/login")
				.invalidateHttpSession(true)
				)
                .build();
    }
    
    

    
    
    
} // end config
=======
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, JwtProvider jwtProvider) throws Exception {
        return http
                .csrf().disable()
                .cors()
                .and()
                
                
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().permitAll()
                )
                
//                .authorizeHttpRequests(auth -> auth
//                        // 로그인, 회원가입
//                        .requestMatchers(
//                            "/auth/login",
//                            "/auth/register",
//                            "/auth/refresh",
//                            "/css/**", "/js/**", "/img/**"
//                        ).permitAll()
                
//                .sessionManagement()
//                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                .and()
                .addFilterBefore(new JwtAuthenticationFilter(jwtProvider), UsernamePasswordAuthenticationFilter.class)

                .logout(logout -> logout
                        .disable()
                )
                .build();
    }
}
>>>>>>> Stashed changes
