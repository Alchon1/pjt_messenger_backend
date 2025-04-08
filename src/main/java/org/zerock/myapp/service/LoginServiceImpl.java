package org.zerock.myapp.service;

import java.util.Optional;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.zerock.myapp.domain.EmployeeDTO;
import org.zerock.myapp.entity.Employee;
import org.zerock.myapp.persistence.LoginRepository;

@Service
public class LoginServiceImpl {

	private final LoginRepository loginRepository = null;
	private final BCryptPasswordEncoder bcryptPasswordEncoder = new BCryptPasswordEncoder();
	
	// ================= 로그인 로직 =======================

	public Optional<Employee> findByLoginId(String loginId) {
		return loginRepository.findByLoginId(loginId);
		
	}  // 사용자의 아이디 가 db 에 저장이 되어 있는지 검색

	public boolean checkPassword(String rawPassword, String encodedPassword) {
	    return bcryptPasswordEncoder.matches(rawPassword, encodedPassword);
	} // 비번이 일치하는지 검증.


	public Optional<Employee> login(String loginId, String password) {
		Optional<Employee> loginOptional = loginRepository.findByLoginId(loginId);
		
		if (loginOptional.isEmpty()) {
			throw new IllegalArgumentException("아이디 또는 패스워드를 입력해주세요.");
		} // 아이디 & 비밀번호 창이 비어있는 경우 검증
		
		Employee employee = loginOptional.get();
		
		// checkPassword = bcryptPasswordEncoder.matches 의 rapper 메소드
		if (! bcryptPasswordEncoder.matches(password, employee.getPassword())) {
			throw new IllegalArgumentException("아이디 또는 패스워드가 틀립니다.");
		} // 비밀번호 검증.
		
	
		return loginOptional;
		
	} // end login


	private boolean bcryptPasswordEncoder(String password, String password2) {
		// TODO Auto-generated method stub
		return false;
	}

	// ===================================================



	// ================= 회원가입 로직 =======================

     // 사번 생성 로직 필요. 시퀀스 부분 확인 필요
	public void registerMember(EmployeeDTO dto) {
		Employee employee = new Employee();
		
		employee.setEmpno(dto.getEmpno()); // 사번
		employee.setName(dto.getName()); // 사원 이름 _ front
		employee.setPosition(dto.getPosition()); // 직급 _ front
		employee.setEmail(dto.getEmail());     // 이메일 _ front
		employee.setLoginId(dto.getLoginId()); // 아이디 _ front 
		employee.setPassword(dto.getPassword()); // 비밀번호. _ front
		employee.setPassword(bcryptPasswordEncoder.encode(employee.getPassword()));  // 비밀번호 암호화 저장
		employee.setTel(dto.getTel()); // 전화번호 _ front
		employee.setAddress(dto.getAddress()); // 사원 주소 _ front
		employee.setZipCode(dto.getZipCode()); // 사원 우편번호 _ front 
		employee.setEnabled(dto.getEnabled()); // 0 - 비활성화, 1- 유효
		employee.setCrtDate(dto.getCrtDate());// 생성날짜. 
		employee.setUdtDate(dto.getUdtDate()); // 수정날짜.
		employee.setEmpSeq(dto.getEmpSeq()); // 시퀀스

		
		loginRepository.save(employee); // db에 저장.
	} // 회원가입 로직. 


	public String checkIdDuplicate(String loginId) {
		boolean isDuplicate = loginRepository.existsByLoginId(loginId);
		
		if (isDuplicate) {
			return "이미 사용 중인 아이디입니다";
		} else  {
			return "사용 가능한 아이디 입니다.";
		}
		
	} // 아이디 중복체크 로직 
	   
	// ===================================================
	
	
	
}
