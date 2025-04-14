package org.zerock.myapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.zerock.myapp.domain.ChatDTO;
import org.zerock.myapp.domain.ChatEmployeeDTO;
import org.zerock.myapp.domain.ChatInitResponseDTO;
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
//@RequestMapping("/api")
@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class ChatController {
		
	@Autowired private ChatService chatService;
    @Autowired private EmployeeService empService;
    @Autowired private ProjectService pjService;
    @Autowired private DepartmentService dtService;
    
	@GetMapping
	List<ChatDTO> list() { // 리스트
		log.debug("list() invoked.");
		
		return chatService.findAllList();
	} // list

	
	// 등록 처리
	@PostMapping
	ChatDTO register(@RequestBody ChatDTO dto) { // 등록 처리
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
			@PathVariable Long id
			) {
		log.debug("delete({}) invoked.",id);
		
		return chatService.deleteById(id);
	} // delete
	
	@PostMapping("/{chatId}/invite")
	public List<ChatEmployeeDTO> inviteEmployees(
	    @PathVariable Long chatId,
	    @RequestBody List<ChatEmployeeDTO> inviteList) {
		
	    return chatService.inviteEmployeesToChat(chatId, inviteList);
	}
	
	@GetMapping("/init")
	   public ChatInitResponseDTO getEmployeesAndProjects() {
	      
	      List<Employee> empList=empService.getAllList();
	      List<Project> pjList=pjService.getAllList();
	      List<Department> dtList=dtService.getAllList();
	      
	      return new ChatInitResponseDTO(empList,pjList, dtList);

   }
	
} // end class
