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
import org.zerock.myapp.domain.ChatDTO;
import org.zerock.myapp.entity.Chat;
import org.zerock.myapp.entity.Project;
import org.zerock.myapp.persistence.ProjectRepository;
import org.zerock.myapp.service.ChatService;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;


/**
 * 채팅 Controller
 */

@Slf4j
@NoArgsConstructor

@RequestMapping("/chat")
@RestController
public class ChatController {
		
	@Autowired private ChatService chatService;
    @Autowired private ProjectRepository projectRepo;
    
	@GetMapping
	List<Chat> list() { // 리스트
		log.debug("list() invoked.");
		
		List<Chat> result = this.chatService.findAllList();
		
		return result;
	} // list
	
//	@GetMapping
//	Optional<Chat> myList() { // 리스트
//		log.debug("list() invoked.");
//		
//		return this.chatService.findMyList();
//	} // list
	
	@PostMapping
	Boolean register(@ModelAttribute ChatDTO dto, @RequestParam String empno) { // 등록 처리
		log.debug("register() invoked.");
		
		return chatService.createRoom(dto, empno);
	} // register
	
	@GetMapping(path = "/{id}")
	ChatDTO read( // 세부 조회
			@PathVariable Long id
			) {
		log.debug("read({}) invoked.",id);
		
		return chatService.getById(id);
	} // read
	
	@PutMapping(path = "/{id}")
	Boolean update( // 수정 처리
			@ModelAttribute ChatDTO dto,
			@PathVariable Long id
			) { 
		log.debug("update({}) invoked.",id);
		
		return chatService.update(dto,id);
	} // update
	
	@DeleteMapping(path = "/{id}")
	Boolean delete( // 삭제 처리
			@PathVariable Long id,
			@RequestParam String empno
			) {
		log.debug("delete({}) invoked.",id);
		
		return chatService.deleteById(id,empno);
	} // delete
	
	
	@GetMapping("/project")
	List<Project> projectList() { // 리스트
		log.debug("projectList() invoked.");
		
		List<Project> result = this.projectRepo.findByEnabled(true);
		
		return result;
	} // list
	
	
} // end class
