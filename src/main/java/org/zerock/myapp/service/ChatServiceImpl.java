package org.zerock.myapp.service;

import java.util.List;
import java.util.Vector;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.zerock.myapp.domain.ChatDTO;
import org.zerock.myapp.entity.Chat;
import org.zerock.myapp.entity.ChatEmployee;
import org.zerock.myapp.entity.ChatEmployeePK;
import org.zerock.myapp.entity.Employee;
import org.zerock.myapp.persistence.ChatEmployeeRepository;
import org.zerock.myapp.persistence.ChatRepository;
import org.zerock.myapp.persistence.EmployeeRepository;
import org.zerock.myapp.persistence.MessageRepository;
import org.zerock.myapp.persistence.ProjectRepository;

import jakarta.annotation.PostConstruct;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@NoArgsConstructor

@Service
public class ChatServiceImpl implements ChatService {
	
    @Autowired ChatRepository chatRepository;
    @Autowired ChatEmployeeRepository chatEmployeeRepository;
    @Autowired EmployeeRepository employeeRepository;
    @Autowired MessageRepository messageRepository;
	@Autowired ProjectRepository projectRepository;
    
	@PostConstruct
    void postConstruct(){
        log.debug("ChatServiceImpl -- postConstruct() invoked");
        log.debug("dao: {}", chatRepository);
    }//postConstruct


	@Override
	public List<Chat> findAllList() {	//검색 없는 전체 리스트
		log.debug("ChatServiceImpl -- getAllList() invoked");
		
		List<Chat> chatList = this.chatRepository.findAllByEnabled(true);
		
		return chatList;

	} // getAllList
	
	@Override
	public List<Chat> getSearchList(ChatDTO dto) {	//검색 있는 전체 리스트
		log.debug("ChatServiceImpl -- getSearchList(()) invoked", dto);

		List<Chat> list = new Vector<>();
		log.debug("리포지토리 미 생성");
		
		return list;
	} // getSearchList
	
	@Override
	public Boolean createRoom(ChatDTO dto) {
	    log.debug("ChatServiceImpl -- createRoom({}) invoked", dto);
	    
	    try {
		    // 1. Chat 엔티티 생성 및 기본값 세팅
		    Chat chat = new Chat();
		    chat.setName(dto.getName());
		    chat.setProject(projectRepository.findById(dto.getProject().getId()).
		    		orElseThrow(() -> new IllegalArgumentException("해당 프로젝트가 존재하지 않습니다.")));
		    
		    Chat savedChat = chatRepository.save(chat);
		    
		    ChatEmployee chatEmployee = new ChatEmployee();
		    
		    for (ChatEmployee chatEmp : dto.getChatEmployees()) {
		        // 사원 조회
		        Employee emp = this.employeeRepository.findById(chatEmp.getEmployee().getEmpno())
		            .orElseThrow(() -> new RuntimeException("사원 없음"));
	
		        // 복합키 생성
		        ChatEmployeePK pk = new ChatEmployeePK();
		        pk.setChatId(chat.getId());
		        pk.setEmpno(emp.getEmpno());
	
		        // ChatEmployee 및 세팅
		        chatEmployee.setId(pk);
		        chatEmployee.setChat(savedChat);  
		        chatEmployee.setEmployee(emp);  
	
		        // 저장
		        chatEmployeeRepository.save(chatEmployee);
		    } // for
		    return true;
	    } catch (Exception e) {
	    	log.error("Create failed: {}", e.getMessage(), e);
	    	return false;
	    }
	   
	}// createRoom
	
	@Override
	public ChatDTO getById(Long id) {	// 단일 조회
		log.debug("ChatServiceImpl -- getById({}) invoked", id);
		
		Chat chat = this.chatRepository.findById(id).
				orElseThrow(() -> new RuntimeException("채팅방 없음"));
		
		ChatDTO dto = chat.toDTO();
		
		List<ChatEmployee> selectedChatEmployee = this.chatEmployeeRepository.findByIdChatId(id);
	
		dto.setChatEmployees(selectedChatEmployee);
		
		
		return dto;

	} // getById
	
	@Override
	public Boolean update(ChatDTO dto) {//수정 처리
		log.debug("ChatServiceImpl -- update({}) invoked", dto);

		try {
			
			
		
			for (String empno : dto.getEmpnos()) {
		        // 사원 조회
				ChatEmployee chatEmployee = new ChatEmployee();
				
		        Employee emp = this.employeeRepository.findById(empno)
		            .orElseThrow(() -> new RuntimeException("사원 없음"));
	
		        // 복합키 생성
		        ChatEmployeePK pk = new ChatEmployeePK();
		        pk.setChatId(dto.getId());
		        pk.setEmpno(empno);
	
		        // ChatEmployee 및 세팅
		        chatEmployee.setId(pk);
		        chatEmployee.setChat(this.chatRepository.findById(dto.getId()).get());  
		        chatEmployee.setEmployee(emp);  
		        
		        // 저장
		        chatEmployeeRepository.save(chatEmployee);
		    } // for
		    return true;
	    } catch (Exception e) {
	    	log.error("Update failed: {}", e.getMessage(), e);
	    	return false;
	    } // try-catch

	} // update

	@Override
	public Boolean deleteById(Long id, String empno) { // 삭제 처리
		log.debug("ChatServiceImpl -- deleteById({}) invoked", id);

		try {
		// 채팅방 나가기 기능
		ChatEmployee chatEmployee = this.chatEmployeeRepository.findByIdChatIdAndIdEmpno(id,empno);
		chatEmployee.setEnabled(false);

		
		this.chatEmployeeRepository.save(chatEmployee);
		// 사람이 아무도 없을 경우 채팅방 삭제
		if(this.chatEmployeeRepository.findByEnabledAndIdChatId(true,id).isEmpty()) {
			Chat chat = this.chatRepository.findById(id).get();
			chat.setEnabled(false);
			this.chatRepository.save(chat);
		} // if

		return true;
		} catch(Exception e) {
			log.error("Delete failed: {}", e.getMessage(), e);
			return false;
		}
	} // deleteById

}//end class