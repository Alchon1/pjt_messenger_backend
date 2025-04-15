package org.zerock.myapp.service;

import java.util.Optional;

<<<<<<< Updated upstream
import org.zerock.myapp.entity.Employee;

public interface LoginService {

	
	// 아이디 검증
	public abstract Optional<Employee> findByLoginId(String loginId);
	// 회원가입시 똑같은 아이디가 db에 저장되어 있는지 검증.
	public abstract Boolean existsByLoginId(String loginId);
	
} // end class
=======
import org.springframework.stereotype.Service;
import org.zerock.myapp.entity.Employee;

@Service
public interface LoginService {

	public abstract Optional<Employee>findByLoginId(String loginId);
	public abstract Boolean checkPassword(String rowPassword, String encodedPassword);
	public abstract Optional<Employee> login(String loginId, String password);

} // end service
>>>>>>> Stashed changes
