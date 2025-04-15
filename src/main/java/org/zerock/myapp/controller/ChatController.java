package org.zerock.myapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.zerock.myapp.domain.ChatDTO;
import org.zerock.myapp.domain.ChatInitResponseDTO;
import org.zerock.myapp.entity.Chat;
import org.zerock.myapp.entity.Department;
import org.zerock.myapp.entity.Employee;
import org.zerock.myapp.entity.Project;
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
@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class ChatController {
		
	@Autowired private ChatService chatService;
	@Autowired private EmployeeService empService;
	@Autowired private ProjectService pjService;
	@Autowired private DepartmentService dtService;
	
	@GetMapping("/init")
	public ChatInitResponseDTO getEmployeesAndProjects() {
		
		List<Employee> empList=empService.getAllList();
		List<Project> pjList=pjService.getAllList();
		List<Department> dtList=dtService.getAllList();
		
		return new ChatInitResponseDTO(empList,pjList, dtList);

	}

	
	
	// 리스트
	@GetMapping
//	List<ChatDTO> list() { 
//		log.debug("list() invoked.");
//		
//		return chatService.findAllList();
//	} // list

	
	
	// 등록 처리
	@PostMapping
	public ResponseEntity<Chat> register(
		    @RequestParam String roomName,
		    @RequestParam Long projectId,
		    @RequestParam List<String> invited
		    
//			@RequestParam  ChatDTO dto
			) { 
		log.debug("register() invoked.");
	    log.info("📦 방이름: {}, 프로젝트ID: {}, 초대자들: {}", roomName, projectId, invited);

	    chatService.createRoom(roomName, projectId, invited);
	    return ResponseEntity.ok().build();
		
		
//		return chatService.createRoom(dto);
	} // register
	
	
	// 세부 조회
	@GetMapping(path = "/{id}")
	Chat read( 
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
			@PathVariable Long id
			) {
		log.debug("delete({}) invoked.",id);
		
		return chatService.deleteById(id);
	} // delete
	

} // end class
