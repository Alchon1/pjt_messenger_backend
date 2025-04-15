package org.zerock.myapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.zerock.myapp.domain.WorkDTO;
import org.zerock.myapp.entity.Work;
import org.zerock.myapp.service.WorkServiceImpl;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;


/**
 * 업무 Controller
 */

@Slf4j
@NoArgsConstructor

@RequestMapping("/work")
@RestController
public class WorkController {
	@Autowired private WorkServiceImpl service;
	
	@GetMapping
	List<Work> list(
			WorkDTO dto
			) { // 리스트
		log.debug("list({}) invoked.",dto);
		
		if("managed".equalsIgnoreCase(dto.getWork())) {
			// 업무 담당자 조회
			List<Work> managedWorks = this.service.getManagedWorksByEnabledAndEmpno(true, dto.getEmployee().getEmpno());
			managedWorks.forEach(s -> log.info(s.toString()));
			
			return managedWorks;
		} else if("requested".equalsIgnoreCase(dto.getWork())) {
			// 업무 요청자 조회
			List<Work> requestedWorks = this.service.getWorksByEnabledAndEmployee(true, dto.getEmployee());
			requestedWorks.forEach(s -> log.info(s.toString()));
			
			return requestedWorks;
		} else {
			throw new IllegalArgumentException("Invalid work type: " + dto.getWork());
		} // if-else
		
	} // list
	
	@PostMapping
	Boolean register(
			@ModelAttribute WorkDTO dto,
			@RequestParam(required = false) List<String> empnos
			) { // 등록 처리
		log.debug("register({}, {}) invoked.",dto,empnos);
		
		Boolean result = this.service.createWorkWithEmployees(dto, empnos);
		
		return result;
	} // register
	
	@GetMapping(path = "/{id}")
	WorkDTO read( // 세부 조회
			@PathVariable Long id
			) {
		log.debug("read({}) invoked.",id);
		
		WorkDTO work = this.service.getById(id);
		log.info("work is ({})",work);
		
		return work;
	} // read
	
	@PutMapping(path = "/{id}")
	Boolean update( // 수정 처리
			@PathVariable Long id,
			@ModelAttribute WorkDTO dto,
			@RequestParam(required = false) List<String> empnos
			) { 
		log.debug("update({},{}) invoked.",id,dto, empnos);
		
		dto.setId(id); // DTO에 ID 설정
	    Boolean result = this.service.update(dto, empnos); // 서비스 호출
	    return result;
	} // update
	
	@DeleteMapping(path = "/{id}")
	Boolean delete( // 삭제 처리
			@PathVariable Long id
			) {
		log.debug("delete({}) invoked.",id);
		
		Boolean result = this.service.deleteById(id); // 서비스 호출
		return result;
	} // delete
	
} // end class
