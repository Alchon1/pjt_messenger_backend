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
import org.springframework.web.multipart.MultipartFile;
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
	Page<Employee> list(
			@ModelAttribute EmployeeDTO dto,
			@RequestParam(name = "currPage", required = false, defaultValue = "0") Integer currPage, // 페이지 시작 값은 0부터
			@RequestParam(name = "pageSize", required = false, defaultValue = "8") Integer pageSize // 기본 페이지 사이즈 8
		) { // 리스트
		log.debug("list() invoked.");
		log.debug("dto: {}, currPage: {}, pageSize: {}", dto, currPage, pageSize);
		
		Pageable paging = PageRequest.of(currPage, pageSize, Sort.by("position", "empno").descending());	// Pageable 설정
		Page<Employee> list = this.service.getSearchList(dto, paging);
		
		list.forEach(p -> log.info(p.toString()));
		
		return list;
	} // list

	@GetMapping("/selectlist")
	public ResponseEntity<List<EmployeeHierarchyDTO>> getGroupLeaders() {
	    List<EmployeeHierarchyDTO> result = service.findByEnabledAndPositionInOrderByDepartment();
	    return ResponseEntity.ok(result);
	} // 부서장 + 팀장
	
//	@GetMapping("/department/{deptId}")
//	public ResponseEntity<?> getEmployeesByDepartment(@PathVariable Long deptId) {
//	    List<Employee> employees = service.getEmployeesByDepartmentId(deptId);
//	    if (employees.isEmpty()) {
//	    	return ResponseEntity
//	    			.status(HttpStatus.NOT_FOUND)
//					.body("해당 부서에 사원이 없습니다");
//	    }
//	    return ResponseEntity.ok(employees);
//	}

	@PostMapping("/register")
	ResponseEntity<?> register(
			@ModelAttribute EmployeeDTO dto,
			@RequestParam(required = false) MultipartFile file					
			) { // 등록 처리
		log.debug("register() invoked.");

		this.service.create(dto, file);

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
			@PathVariable String empno, 
			@ModelAttribute EmployeeDTO dto,
			@RequestParam(required = false) MultipartFile file
			) {
		log.debug("update({}) invoked.", empno);

		this.service.update(empno, dto, file);

		return ResponseEntity.ok("사원정보 수정 완료");
	} // update

	@DeleteMapping(path = "/{id}")
	String delete( // 삭제 처리
			@PathVariable Long id) {
		log.debug("delete({}) invoked.", id);

		return "delete";
	} // delete

} // end class
