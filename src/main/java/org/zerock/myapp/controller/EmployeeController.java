package org.zerock.myapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.zerock.myapp.domain.EmployeeDTO;
import org.zerock.myapp.entity.Employee;
import org.zerock.myapp.persistence.EmployeeRepository;
import org.zerock.myapp.service.EmployeeService;
import org.zerock.myapp.service.LoginServiceImpl;

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
	
	@Autowired LoginServiceImpl loginService;
	@Autowired EmployeeService employeeService; 
	@Autowired EmployeeRepository Repo;
	
	@GetMapping
	List<Employee> list() { // 리스트
		log.debug("list() invoked.");
		
		return employeeService.getAllList();
	} // list
	
	@PostMapping("/register")
	 ResponseEntity<?> register(@ModelAttribute EmployeeDTO dto) {
		
		 loginService.registerEmployee(dto);
		 
		 return ResponseEntity.ok("회원가입 완료");
		
	 }
	
	@GetMapping(path = "/{id}")
	String read( // 세부 조회
			@PathVariable Long id
			) {
		log.debug("read({}) invoked.",id);
		
		return "read";
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
