package org.zerock.myapp.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@NoArgsConstructor

@RequestMapping("/project")
@RestController
public class ProjectController {
	
	@GetMapping
	String list() { // 리스트
		log.debug("list() invoked.");
		
		return "list";
	} // list
	
	@PostMapping
	String register() { // 등록
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
	String update( // 수정
			@PathVariable Long id
			) { 
		log.debug("update({}) invoked.",id);
		
		return "update";
	} // update
	
	@DeleteMapping(path = "/{id}")
	String delete( // 삭제
			@PathVariable Long id
			) {
		log.debug("delete({}) invoked.",id);
		
		return "delete";
	} // delete
	

} // end class
