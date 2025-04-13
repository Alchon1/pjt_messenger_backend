package org.zerock.myapp.config;

import java.io.IOException;
import java.util.Collection;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.zerock.myapp.util.RoleUtil;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class LoginSuccessUrlHandler implements AuthenticationSuccessHandler {
	
	@Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException {

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

        for (GrantedAuthority auth : authorities) {
            String role = auth.getAuthority(); // ROLE_XXX
            String redirectUrl = RoleUtil.getRedirectUrlByRole(role);  // RoleUtil 에서 가져온다.

            response.sendRedirect(redirectUrl);
            return;
        }

        // 권한이 하나도 없을 때
        response.sendRedirect("/auth/login");
    }

}
