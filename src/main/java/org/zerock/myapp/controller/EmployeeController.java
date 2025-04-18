package org.zerock.myapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.zerock.myapp.domain.EmployeeDTO;
import org.zerock.myapp.entity.Employee;
import org.zerock.myapp.service.EmployeeService;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 사원 관리 Controller
 */

@Slf4j
@NoArgsConstructor

@RequestMapping("/employee")
@RestController
public class EmployeeController {

	@Autowired
	EmployeeService service;

	@GetMapping
	public ResponseEntity<Page<Employee>> getAllEmployees(
			@RequestParam(name = "currPage", required = false, defaultValue = "0") Integer currPage, // 페이지 시작 값은 0부터
			@RequestParam(name = "pageSize", required = false, defaultValue = "10") Integer pageSize // 기본 페이지 사이즈 10

	) {
		Pageable Pageable = PageRequest.of(currPage, pageSize, Sort.by("empno").descending());
		Page<Employee> list = service.getAllList(Pageable);
	    return ResponseEntity.ok(list);
	}
	
	@GetMapping("/search")
	public ResponseEntity<Page<Employee>> searchEmployees(
	        @ModelAttribute EmployeeDTO dto,
			@RequestParam(name = "currPage", required = false, defaultValue = "0") Integer currPage, // 페이지 시작 값은 0부터
			@RequestParam(name = "pageSize", required = false, defaultValue = "10") Integer pageSize // 기본 페이지 사이즈 10

	) {
		Pageable Pageable = PageRequest.of(currPage, pageSize, Sort.by("empno").descending());
	    Page<Employee> result = service.getSearchList(dto, Pageable);
	    return ResponseEntity.ok(result);
	}

	@GetMapping("/selectlist")
	public List<Employee> selectList() {

		return service.getPositionsList();
	} // 팀장 + 부서장

	@PostMapping("/register")
	ResponseEntity<?> register(@ModelAttribute EmployeeDTO dto) { // 등록 처리
		log.debug("register() invoked.");

		this.service.create(dto);

		return ResponseEntity.ok("사원등록 완료");
	} // register

	@GetMapping("/checkId")
	Boolean checkIdDuplicate(String loginId) {

		return service.checkIdDuplicate(loginId);
	} // 중복확인

	@GetMapping(path = "/{id}")
	Employee read( // 세부 조회
			@PathVariable String id) {
		log.debug("read({}) invoked.", id);

		Employee read = this.service.getById(id);

		return read;
	} // read

	@PutMapping(path = "/{empno}")
	ResponseEntity<?> update( // 수정 처리
			@PathVariable String empno, @ModelAttribute EmployeeDTO dto) {
		log.debug("update({}) invoked.", empno);

		this.service.update(empno, dto);

		return ResponseEntity.ok("사원정보 수정 완료");
	} // update

	@DeleteMapping(path = "/{id}")
	String delete( // 삭제 처리
			@PathVariable Long id) {
		log.debug("delete({}) invoked.", id);

		return "delete";
	} // delete

} // end class
