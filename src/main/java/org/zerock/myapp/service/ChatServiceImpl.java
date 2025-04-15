package org.zerock.myapp.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.zerock.myapp.domain.ChatDTO;
import org.zerock.myapp.domain.ChatEmployeeDTO;
import org.zerock.myapp.entity.Chat;
import org.zerock.myapp.entity.ChatEmployee;
import org.zerock.myapp.entity.ChatEmployeePK;
import org.zerock.myapp.entity.Employee;
import org.zerock.myapp.entity.Message;
import org.zerock.myapp.persistence.ChatEmployeeRepository;
import org.zerock.myapp.persistence.ChatRepository;
import org.zerock.myapp.persistence.EmployeeRepository;
import org.zerock.myapp.persistence.MessageRepository;
import org.zerock.myapp.persistence.ProjectRepository;

import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityNotFoundException;
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
		
		List<Chat> chatList = this.chatRepository
				.findAllByEnabled(true)			
				;
		
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
	public Chat createRoom(ChatDTO dto) {
	    log.debug("ChatServiceImpl -- createRoom({}) invoked", dto);

	    // 1. Chat 엔티티 생성 및 기본값 세팅
	    Chat chat = new Chat();
	    chat.setName(dto.getName());
	    chat.setProject(projectRepository.findById(dto.getProjectId()).
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
	    }
	   
	    return chat;
	}// createRoom
	
//	@Override
//	public Chat getById(Long id) {	// 단일 조회
//		log.debug("ChatServiceImpl -- getById({}) invoked", id);
//		
//		Chat chat = new Chat();
//		
//		Chat selectedChat = this.chatRepository.findById(id).orElse(new Chat());	
//		List<ChatEmployee> selectedChatEmployee = this.chatEmployeeRepository.findByIdChatId(id);
//		
//		chat.setId(selectedChat.getId());
//		chat.setName(selectedChat.getName());
//		List<Message> messages = messageRepository.findByChatId(id);
//		chat.setMessages(messages);
//		chat.setProject(selectedChat.getProject());
//		
//		List<ChatEmployee> chatEmployeeDTOList = new ArrayList<>();
//	    
//	    for (ChatEmployee chatEmployee : selectedChatEmployee) {
//	        
//	        ChatEmployeeDTO chatEmployeeDTO = new ChatEmployeeDTO();
//	        
//	        chatEmployeeDTO.setEmployee(chatEmployee.getEmployee());
//	        chatEmployeeDTO.setChatId(id);
//	        
//	        chatEmployeeDTOList.add(chatEmployeeDTO);
//	    }
//	    chat.setChatEmployees(chatEmployeeDTOList);
//		
//		return chat;
//	} // getById
	
	@Override
	public Boolean update(ChatDTO dto) {//수정 처리
		log.debug("ChatServiceImpl -- update({}) invoked", dto);

	    Chat chat = this.chatRepository.findById(dto.getId())
	        .orElseThrow(() -> new EntityNotFoundException("채팅방이 존재하지 않습니다."));

	    chat.setUdtDate(LocalDateTime.now());
	    
	    this.chatRepository.save(chat);
	    
		Boolean isUpdate = true;
		return isUpdate;
	} // update

	@Override
	public Boolean deleteById(Long id) { // 삭제 처리
		log.debug("ChatServiceImpl -- deleteById({}) invoked", id);
		
		Chat chat = this.chatRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("채팅방이 존재하지 않습니다."));;
		
		chat.setEnabled(false);
		
		this.chatRepository.save(chat);
		
		return true;
	} // deleteById
	
	@Override
	public List<ChatEmployeeDTO> inviteEmployeesToChat(Long chatId, List<ChatEmployeeDTO> empnos) {
	    // 초대 로직
		
		// 1. 채팅방 존재 확인
	    Chat chat = chatRepository.findById(chatId)
	        .orElseThrow(() -> new RuntimeException("해당 채팅방이 존재하지 않습니다."));

	    for (ChatEmployeeDTO chatEmployeeDTO : empnos) {
	        // 2. 사원 존재 확인
	        Employee emp = employeeRepository.findById(chatEmployeeDTO.getEmployee().getEmpno())
	            .orElseThrow(() -> new RuntimeException("사원 번호 " + chatEmployeeDTO + "가 존재하지 않습니다."));

	        // 3. 중복 초대 방지
	        ChatEmployeePK pk = new ChatEmployeePK();
	        pk.setChatId(chatId);
	        pk.setEmpno(chatEmployeeDTO.getEmployee().getEmpno());
	        
	        if (chatEmployeeRepository.existsById(pk)) {
	            log.debug("이미 참여 중인 사원: {}", chatEmployeeDTO);
	        } else {
		        // 4. 새로운 ChatEmployee 생성
		        ChatEmployee chatEmployee = new ChatEmployee();
		        chatEmployee.setId(pk);
		        chatEmployee.setChat(chat);
		        chatEmployee.setEmployee(emp);
	
		        // 저장
		        chatEmployeeRepository.save(chatEmployee);
		        
	        }
	    }
	    
	    List<ChatEmployee> chatEmployees = chatEmployeeRepository.findByIdChatId(chatId);
	    List<ChatEmployeeDTO> result = new ArrayList<>();

	    for (ChatEmployee chatEmp : chatEmployees) {
	        ChatEmployeeDTO dto = new ChatEmployeeDTO();
	        dto.setEmployee(chatEmp.getEmployee());
	        result.add(dto);
	    }
	   
	   return result;
	}// inviteEmployeesToChat
	
	
//	public void exitChatRoom(Long chatId,String empno) {
//        
//        ChatEmployee employee = chatEmployeeRepository.findByIdChatIdAndEmployeeId(chatId, empno);
//        
//        
//    }
	
}//end class