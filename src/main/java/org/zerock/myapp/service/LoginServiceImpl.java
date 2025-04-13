package org.zerock.myapp.service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Optional;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.zerock.myapp.domain.EmployeeDTO;
import org.zerock.myapp.entity.Employee;
import org.zerock.myapp.persistence.LoginRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LoginServiceImpl {

	private final LoginRepository loginRepository;
	private final BCryptPasswordEncoder bcryptPasswordEncoder;
	
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



	// ================= 회원가입 로직 =======================

     // 사번 생성 로직 필요. 시퀀스 부분 확인 필요
	public Employee registerEmployee(EmployeeDTO dto) {
	
		try {
		Employee employee = new Employee();
		// 사번 생성 로직2 ( 테스트 중 )
        String prefix = getRolePrefixFromPosition(dto.getPosition());
        String empno = generateEmpno(prefix, dto.getCrtDate());
	
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
		

		return loginRepository.save(employee); // db에 저장.
		} catch (Exception e) {
			throw new IllegalArgumentException("회원가입에 실패했습니다. 다시 시도해 주세요.");
		}
	} // 회원가입 로직. 

	// ================= 아이디 중복 확인 =======================

	public String checkIdDuplicate(String loginId) {
		boolean isDuplicate = loginRepository.existsByLoginId(loginId);
		
		if (isDuplicate) {
			return "이미 사용 중인 아이디입니다";
		} else  {
			return "사용 가능한 아이디 입니다.";
		}
		
	} // 아이디 중복체크 로직 
	   

	
	
	// ================= 사번 생성 로직2 ( 테스트 중 ) =======================
	
	// 직급에 따라 알파벳 반환
	private String getRolePrefixFromPosition(Integer position) {
	    return switch (position) {
	        case 1 -> "E"; // 사원
	        case 2 -> "E"; // 팀장
	        case 3 -> "E"; // 부서장
	        case 4 -> "C"; // CEO
	        case 5 -> "H"; // 인사관리자
	        case 9 -> "A"; // 시스템관리자
	        default -> throw new IllegalArgumentException("알 수 없는 직급입니다.");
	    };
	}

	// 날짜와 prefix로 사번 생성
	private String generateEmpno(String rolePrefix, Date date) {
	    LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
	    String year = String.valueOf(localDate.getYear()).substring(2);
	    String month = String.format("%02d", localDate.getMonthValue());

	    int sequence = (int)(Math.random() * 900 + 100); // 현재 방식은 랜덤 sequence 방식이라 수정 필요. 
	    
	    
	    // 달이 바뀌면 sequence 초기화
	    // 랜덤 숫자가 아닌 순서대로 
	    // 갯수로 하면 임의로 데이터를 넣는 순간 충돌 날 수 있음.
	    
	    return rolePrefix + year + month + sequence;
	}
	
	
	
}
