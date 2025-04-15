package org.zerock.myapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.zerock.myapp.domain.ChatDTO;
import org.zerock.myapp.entity.Chat;
import org.zerock.myapp.service.ChatService;
import org.zerock.myapp.service.DepartmentService;
import org.zerock.myapp.service.EmployeeService;
import org.zerock.myapp.service.ProjectService;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;


/**
 * 채팅 Controller
 */

@Slf4j
@NoArgsConstructor

@RequestMapping("/chat")
//@RequestMapping("/api")
@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class ChatController {
		
	@Autowired private ChatService chatService;
    @Autowired private EmployeeService empService;
    @Autowired private ProjectService pjService;
    @Autowired private DepartmentService dtService;
    
	@GetMapping
	List<Chat> list() { // 리스트
		log.debug("list() invoked.");
		
		List<Chat> result = this.chatService.findAllList();
		
		return result;

	} // list

	
	// 등록 처리

	Boolean register(@ModelAttribute ChatDTO dto) { // 등록 처리

		log.debug("register() invoked.");
		
		return chatService.createRoom(dto);
	} // register
	
	
	// 세부 조회
	@GetMapping(path = "/{id}")
	ChatDTO read( // 세부 조회
			@PathVariable Long id
			) {
		log.debug("read({}) invoked.",id);
		
		return chatService.getById(id);
	} // read
	
	
	// 수정 처리
	@PutMapping(path = "/{id}")
	Boolean update(@RequestBody
			ChatDTO dto,
			@PathVariable Long id
			) { 
		log.debug("update({}) invoked.",id);
		
		return chatService.update(dto);
	} // update
	
	@DeleteMapping(path = "/{id}")
	Boolean delete( // 삭제 처리
			@PathVariable Long id,
			@RequestParam String empno
			) {
		log.debug("delete({}) invoked.",id);
		
		return chatService.deleteById(id,empno);
	} // delete
	

	
} // end class
