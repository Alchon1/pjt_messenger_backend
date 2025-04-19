package org.zerock.myapp.controller;

import java.text.ParseException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.zerock.myapp.domain.ProjectDTO;
import org.zerock.myapp.entity.Project;
import org.zerock.myapp.exception.ServiceException;
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
			@RequestParam(name = "currPage", required = false, defaultValue = "1") Integer currPage, // 페이지 시작 값은 0부터
			@RequestParam(name = "pageSize", required = false, defaultValue = "4") Integer pageSize // 기본 페이지 사이즈 8
		) { // 리스트
		log.debug("list() invoked.");
		log.debug("dto: {}, currPage: {}, pageSize: {}", dto, currPage, pageSize);
		
		Pageable paging = PageRequest.of(currPage-1, pageSize, Sort.by("crtDate").descending());	// Pageable 설정
		Page<Project> list = this.service.getSearchList(dto, paging);
		
		list.forEach(p -> log.info(p.toString()));
		
		return list;
	} // list
	
	@GetMapping(path = "/upComing")
	Page<Project> listUpComing() { // 리스트
		log.debug("listUpComing() invoked.");
		
		Pageable paging = PageRequest.of(0, 2, Sort.by("endDate").ascending());	// Pageable 설정
		Page<Project> list = this.service.getUpComingList(paging);
		
		return list;
	} // listUpComing
	
	@GetMapping(path = "/status")
	List<Project> listStatus() { // 리스트
		log.debug("listStatus() invoked.");
		
		List<Project> list = this.service.getStatusAllList();
		
		return list;
	} // listStatus
	
	@PostMapping
	Project register(ProjectDTO dto) throws ServiceException, ParseException { // 등록 처리
		log.debug("register() invoked.");
		log.debug("dto: {}", dto);
				
		Project data = this.service.create(dto);
//		Project data = null;
		
		return data;
	} // register
	
	@GetMapping(path = "/{id}")
	Project read(@PathVariable Long id) { // 세부 조회
		log.debug("read({}) invoked.",id);
		
		Project project = this.service.getById(id);
		
		return project;
	} // read
	
	@PutMapping(path = "/{id}")
	Project update(@PathVariable Long id, ProjectDTO dto) throws ServiceException, ParseException {  // 수정 처리
		log.debug("update({}) invoked.",id);

		Project data = this.service.update(id, dto);	
		
		return data;
	} // update
	
	@DeleteMapping(path = "/{id}")
	Project delete(@PathVariable Long id) throws ServiceException { // 삭제 처리
		log.debug("delete({}) invoked.",id);
		
		Project result = this.service.deleteById(id);
		
		return result;
	} // delete
	

} // end class
