package org.zerock.myapp.service;

import java.util.Optional;

import org.zerock.myapp.entity.Employee;

public interface LoginService {

	
	// 아이디 검증
	public abstract Optional<Employee> findByLoginId(String loginId);
	// 회원가입시 똑같은 아이디가 db에 저장되어 있는지 검증.
	public abstract Boolean existsByLoginId(String loginId);
	
} // end class
