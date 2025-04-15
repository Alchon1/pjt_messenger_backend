package org.zerock.myapp.controller;

<<<<<<< Updated upstream
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
=======
import org.springframework.beans.factory.annotation.Autowired;
>>>>>>> Stashed changes
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.zerock.myapp.domain.EmployeeDTO;
<<<<<<< Updated upstream
import org.zerock.myapp.entity.Employee;
import org.zerock.myapp.persistence.EmployeeRepository;
import org.zerock.myapp.service.EmployeeService;
import org.zerock.myapp.service.LoginServiceImpl;
=======
import org.zerock.myapp.service.EmployeeService;
>>>>>>> Stashed changes

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;


/**
 * 사원 관리 Controller
 */

@Slf4j
@NoArgsConstructor

@RequestMapping("/member")
@RestController
public class EmployeeController {
	
<<<<<<< Updated upstream
	@Autowired LoginServiceImpl loginService;
	@Autowired EmployeeService employeeService; 
	@Autowired EmployeeRepository Repo;
=======
	@Autowired EmployeeService employeeService;
>>>>>>> Stashed changes
	
	@GetMapping
	List<Employee> list() { // 리스트
		log.debug("list() invoked.");
		
		List<Employee> list = employeeService.getAllList();
		
		return list;
	} // list
	
<<<<<<< Updated upstream
	@PostMapping("/register")
	 ResponseEntity<?> register(@ModelAttribute EmployeeDTO dto) {
		
		 loginService.registerEmployee(dto);
		 
		 return ResponseEntity.ok("회원가입 완료");
		
	 }
=======
	@PostMapping
	public Boolean register(EmployeeDTO dto) {
	    log.debug("register(dto) invoked - {}", dto);

	    return employeeService.create(dto);
	} // create

>>>>>>> Stashed changes
	
	@GetMapping(path = "/{id}")
	Employee read( // 세부 조회
			@PathVariable String id ) {
		log.debug("read({}) invoked.",id);
		
		Employee employee = employeeService.getById(id);
		
		return employee;
	} // read
	
	@PutMapping(path = "/{id}")
	String update( // 수정 처리
			@PathVariable Long id
			) { 
		log.debug("update({}) invoked.",id);
		
		return "update";
	} // update
	
	@DeleteMapping(path = "/{id}")
	String delete( // 삭제 처리
			@PathVariable Long id
			) {
		log.debug("delete({}) invoked.",id);
		
		return "delete";
	} // delete
	

} // end class
