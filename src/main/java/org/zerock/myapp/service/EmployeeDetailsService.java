package org.zerock.myapp.service;

import java.util.List;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.zerock.myapp.entity.Employee;
import org.zerock.myapp.persistence.LoginRepository;
import org.zerock.myapp.util.RoleUtil;

@Service
public class EmployeeDetailsService implements UserDetailsService {

    private final LoginRepository loginRepository;

    public EmployeeDetailsService(LoginRepository loginRepository) {
        this.loginRepository = loginRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Employee employee = loginRepository.findByLoginId(username)
                .orElseThrow(() -> new UsernameNotFoundException("직원 없음"));

        String role = RoleUtil.mapPositionToRole(employee.getPosition());

        return new org.springframework.security.core.userdetails.User(
            employee.getLoginId(),
            employee.getPassword(),  // 여기까지는 로그인 검증용 정보
            List.of(new SimpleGrantedAuthority("ROLE_" + role))
        );
    }
}
