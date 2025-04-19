package org.zerock.myapp.service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.zerock.myapp.domain.EmployeeDTO;
import org.zerock.myapp.entity.Department;
import org.zerock.myapp.entity.Employee;
import org.zerock.myapp.persistence.DepartmentRepository;
import org.zerock.myapp.persistence.EmployeeRepository;

import jakarta.annotation.PostConstruct;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@NoArgsConstructor

@Service
public class EmployeeServiceImpl implements EmployeeService {
    @Autowired EmployeeRepository dao;
    @Autowired DepartmentRepository departmentRepository;
    @Autowired BCryptPasswordEncoder bcrypt;

    
    
	@PostConstruct
    void postConstruct(){
        log.debug("EmployeeServiceImpl -- postConstruct() invoked");
        log.debug("dao: {}", dao);
    }//postConstruct
	

	@Override
	public Page<Employee> getSearchList(EmployeeDTO dto, Pageable pageable) {
	    log.debug("EmployeeServiceImpl -- getSearchList({})", dto);

	    String field = dto.getSearchWord();
	    String keyword = dto.getSearchText();

	    if (field == null || keyword == null || keyword.isBlank()) {
	        return dao.findAll(pageable); // 아무것도 없으면 전체 반환
	    }

	    switch (field) {
	        case "name":
	            return dao.findByNameContainingAndEnabledTrue(keyword,pageable);
	        case "tel":
	            return dao.findByTelContainingAndEnabledTrue(keyword,pageable);

	        default:
	            return dao.findAll(pageable);
	    }
	}


	@Override
	public Page<Employee> getAllList(Pageable pageable) {	//검색 없는 전체 리스트
		log.debug("EmployeeServiceImpl -- getAllList() invoked");
		
		Page<Employee> list = dao.findAll(pageable);
		
		
		return list;
	} // getAllList
	
	
	   @Override
	   public List<Employee> getPositionsList() {
	      
	      Integer[] position =  { 2, 3 };
	      List<Employee> positionList = this.dao.findByEnabledAndPositionInOrderByPositionAscCrtDateDesc(true, position);
	      
	      return positionList;
	   }

	
	   @Override
	    public void findByEnabledAndPositionInOrderByDepartment() {

	        Boolean enabled = true;
	        Integer[] positions = new Integer[]{2, 3};

	        List<Object[]> results = dao.findByEnabledAndPositionInOrderByDepartment(enabled, positions);

	        for (Object[] row : results) {
	            String empno = (String) row[0];
	            String name = (String) row[1];
	            String departmentName = (String) row[2];
	            String positionName = (String) row[3];
	            String path = (String) row[4];

	            System.out.printf("사번: %s, 이름: %s, 부서: %s, 직책: %s, 경로: %s\n",
	                    empno, name, departmentName, positionName, path);
	        }
	    }

	
	
	// ================= 회원가입 로직 =======================
	@Override
	public Boolean create(EmployeeDTO dto) {	//등록 처리
		log.debug("EmployeeServiceImpl -- create({}) invoked", dto);
	   
	      try {
	    	  Department department =
	    			  departmentRepository.findById(dto.getDeptId())
	                    .orElseThrow(() -> new IllegalArgumentException("유효하지 않은 부서 ID입니다."));
	    	  
	      Employee employee = new Employee();
	      // 사번 생성 로직2 ( 테스트 중 )
	        String prefix = getRolePrefixFromPosition(dto.getPosition());
	        String empno = generateEmpno(prefix, dto.getCrtDate());
//	   
	      employee.setEmpno(empno); // 사번
	      employee.setName(dto.getName()); // 사원 이름 _ front
	      employee.setPosition(dto.getPosition()); // 직급 _ front
	      employee.setDepartment(department); // 부서 _ front
	      employee.setEmail(dto.getEmail());     // 이메일 _ front
	      employee.setLoginId(dto.getLoginId()); // 아이디 _ front 
	      employee.setPassword(dto.getPassword()); // 비밀번호. _ front
	      employee.setPassword(bcrypt.encode(employee.getPassword()));  // 비밀번호 암호화 저장
	      employee.setTel(dto.getTel()); // 전화번호 _ front
	      employee.setAddress(dto.getAddress()); // 사원 주소 _ front
	      employee.setZipCode(dto.getZipCode()); // 사원 우편번호 _ front 
	      employee.setEnabled(true); // 0 - 비활성화, 1- 유효
	      
	      dao.save(employee);
	      return true; // db에 저장.
	      } catch (Exception e) {
	         throw new IllegalArgumentException("회원가입에 실패했습니다. 다시 시도해 주세요.", e);
	      }
	   } // 회원가입 로직. 

	   // ================= 아이디 중복 확인 =======================
		@Override
	   public Boolean checkIdDuplicate(String loginId) {
	      Boolean isDuplicate = dao.existsByLoginId(loginId);
	      
	     return isDuplicate;
	      
	   } // 아이디 중복체크 로직 
	      
	   // ================= 사번 생성 로직2 ( 테스트 중 ) =======================
	   
	     // 직급에 따라 알파벳 반환
	      @Override
	      public String getRolePrefixFromPosition(Integer position) {
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

	      
	      

	      @Override
	      public String generateEmpno(String rolePrefix, Date date) {
	           LocalDate localDate;
	           
	           if (date == null) {
	               log.warn("generateEmpno: date is null, using current date.");
	               localDate = LocalDate.now();
	           } else {
	               localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
	           }
	           
	           String year = String.valueOf(localDate.getYear()).substring(2);
	           String month = String.format("%02d", localDate.getMonthValue());

	           String basePrefix = rolePrefix + year + month;

	           // 해당 달에 생성된 사번 수 조회 (이건 Repository에 메소드 하나 만들어줘야 함.)
	           long count = dao.countByEmpnoStartingWith(basePrefix);

	           String seq = String.format("%03d", count + 1);

	           return basePrefix + seq;
	       } // 사번 생성

	   
	
	@Override
	public Employee getById(String id) {	// 단일 조회
		log.debug("EmployeeServiceImpl -- getById({}) invoked", id);
		
		//값이 존재하면 반환하고, 없으면 new Course()와 같은 기본값을 반환합니다.
		Optional<Employee> optional = dao.findById(id);
		if (optional.isPresent()) {
			log.debug("Found: {}", optional.get());
			return optional.get();
		} else {
			log.warn("No employee selected: {}", id);
			return null;
		}		
	} // getById
	
	@Override
	public Boolean update(String empno, EmployeeDTO dto) {//수정 처리
		log.debug("EmployeeServiceImpl -- update({}) invoked", dto);
		
		try {
	    	  Department department =
	    			  departmentRepository.findById(dto.getDeptId())
	                    .orElseThrow(() -> new IllegalArgumentException("유효하지 않은 부서 ID입니다."));
	    	  
	    	  Employee employee = dao.findById(dto.getEmpno())
	    	            .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사원입니다."));
	      // 사번 생성 로직2 ( 테스트 중 )
//	        String prefix = getRolePrefixFromPosition(dto.getPosition());
//	        String empno = generateEmpno(prefix, dto.getCrtDate());
//	   
	      employee.setPosition(dto.getPosition()); // 직급 _ front
	      employee.setDepartment(department); // 부서 _ front
	      employee.setEmail(dto.getEmail());     // 이메일 _ front
	      employee.setPassword(dto.getPassword()); // 비밀번호. _ front
	      employee.setPassword(bcrypt.encode(employee.getPassword()));  // 비밀번호 암호화 저장
	      employee.setTel(dto.getTel()); // 전화번호 _ front
	      employee.setAddress(dto.getAddress()); // 사원 주소 _ front
	      employee.setZipCode(dto.getZipCode()); // 사원 우편번호 _ front 
	      
	      dao.save(employee);
	      return true; // db에 저장.
	      } catch (Exception e) {
	         throw new IllegalArgumentException("사원 정보 수정에 실패했습니다. 다시 시도해 주세요.");
	      }
	} // update

	@Override
	public Boolean deleteById(String id) { // 삭제 처리
		log.debug("EmployeeServiceImpl -- deleteById({}) invoked", id);
		
		//dao.deleteById(id);
		return true;
	}
	
	
}//end class
