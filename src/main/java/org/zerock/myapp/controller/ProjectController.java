package org.zerock.myapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.zerock.myapp.domain.ProjectDTO;
import org.zerock.myapp.entity.Project;
import org.zerock.myapp.service.ProjectServiceImpl;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;


/**
 * 프로젝트 Controller
 */

@Slf4j
@NoArgsConstructor

@RequestMapping("/project")
@RestController
public class ProjectController {
	@Autowired ProjectServiceImpl service;
	
	@GetMapping
	Page<Project> list(
			ProjectDTO dto,
			@RequestParam(name = "currPage", required = false, defaultValue = "0") Integer currPage, // 페이지 시작 값은 0부터
			@RequestParam(name = "pageSize", required = false, defaultValue = "8") Integer pageSize // 기본 페이지 사이즈 8
		) { // 리스트
		log.debug("list() invoked.");
		log.debug("dto: {}, currPage: {}, pageSize: {}", dto, currPage, pageSize);
		
		Pageable paging = PageRequest.of(currPage, pageSize, Sort.by("crtDate").descending());	// Pageable 설정
		Page<Project> list = this.service.getSearchList(dto, paging);
		
		list.forEach(p -> log.info(p.toString()));
		
		return list;
	} // list
	
	@GetMapping(path = "/upComming")
	Page<Project> listUpComming() { // 리스트
		log.debug("listUpComming() invoked.");
		
		Pageable paging = PageRequest.of(0, 2, Sort.by("endDate").descending());	// Pageable 설정
		Page<Project> list = this.service.getUpCommingList(paging);
		
		return list;
	} // list
	
	@PostMapping
	String register(ProjectDTO dto) { // 등록 처리
		log.debug("register() invoked.");
		
		return "register";
	} // register
	
	@GetMapping(path = "/{id}")
	Project read(@PathVariable Long id) { // 세부 조회
		log.debug("read({}) invoked.",id);
		
		Project project = this.service.getById(id);
		
		return project;
	} // read
	
	@PutMapping(path = "/{id}")
	String update(@PathVariable Long id, ProjectDTO dto) {  // 수정 처리
		log.debug("update({}) invoked.",id);
		
		return "update: "+id;
	} // update
	
	@DeleteMapping(path = "/{id}")
	String delete(@PathVariable Long id) { // 삭제 처리
		log.debug("delete({}) invoked.",id);
		
		String result = this.service.deleteById(id);
		
		return result;
	} // delete
	

} // end class
