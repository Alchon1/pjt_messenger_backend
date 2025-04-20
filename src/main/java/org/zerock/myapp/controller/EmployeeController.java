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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.zerock.myapp.domain.EmployeeDTO;
import org.zerock.myapp.domain.EmployeeHierarchyDTO;
import org.zerock.myapp.entity.Employee;
import org.zerock.myapp.persistence.DepartmentRepository;
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
	
	@Autowired
	DepartmentRepository dao;

	@GetMapping
	public List<Employee> list() { // 리스트
		log.debug("list() invoked.");

		List<Employee> list = service.getAllList();

		return list;
	} // list

	@GetMapping("/search")
	public ResponseEntity<List<Employee>> searchEmployees(@RequestParam String searchWord,
			@RequestParam String searchText) {
		EmployeeDTO dto = new EmployeeDTO();
		dto.setSearchWord(searchWord);
		dto.setSearchText(searchText);

		List<Employee> result = service.getSearchList(dto);
		return ResponseEntity.ok(result);
	}

	@GetMapping("/selectlist")
	public ResponseEntity<List<EmployeeHierarchyDTO>> getGroupLeaders() {
	    List<EmployeeHierarchyDTO> result = service.findByEnabledAndPositionInOrderByDepartment();
	    return ResponseEntity.ok(result);
	} // 부서장 + 팀장
	
	@GetMapping("/department/{deptId}")
	public ResponseEntity<List<Employee>> getEmployeesByDepartment(@PathVariable Long deptId) {
	    List<Employee> employees = service.getEmployeesByDepartmentId(deptId);
	    return ResponseEntity.ok(employees);
	}

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
