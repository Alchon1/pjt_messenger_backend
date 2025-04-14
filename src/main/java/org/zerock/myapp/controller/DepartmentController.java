package org.zerock.myapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.zerock.myapp.domain.DepartmentByOrgaDTO;
import org.zerock.myapp.entity.Department;
import org.zerock.myapp.persistence.DepartmentRepository;
import org.zerock.myapp.service.DepartmentService;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;


/**
 * 부서 Controller
 */

@Slf4j
@NoArgsConstructor

@RequestMapping("/department")
@RestController
public class DepartmentController {
	
	@GetMapping
	String list() { // 리스트
		log.debug("list() invoked.");
		
		return "list";
	} // list
	
	@PostMapping
	String register() { // 등록 처리
		log.debug("register() invoked.");
		
		return "register";
	} // register
	
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
	
	
	@Autowired
    private DepartmentRepository deptRepo;

    @Autowired
    private DepartmentService deptService;
   
   @GetMapping("/tree")
   public ResponseEntity<DepartmentByOrgaDTO> getDepartmentTree() {
       Department root = deptRepo.findById(1L).orElseThrow(); // 최상위 부서 ID를 기준
       //       return ResponseEntity.ok(deptService.buildTree(root)); // root는 Department
       return ResponseEntity.ok(deptService.buildTree(1L)); // root는 Department
   }

} // end class
