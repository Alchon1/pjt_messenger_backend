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
	
	@PostConstruct
    void postConstruct(){
        log.debug("ChatServiceImpl -- postConstruct() invoked");
        log.debug("dao: {}", chatRepository);
    }//postConstruct


	@Override
	public List<ChatDTO> findAllList() {	//검색 없는 전체 리스트
		log.debug("ChatServiceImpl -- getAllList() invoked");
		
		List<Chat> chatList = this.chatRepository.findAllByEnabled(true);
		
		List<ChatDTO> chatDtoList = new Vector<>();
		
		for (Chat chat : chatList) {
	        ChatDTO dto = new ChatDTO();
	        dto.setId(chat.getId());
	        dto.setName(chat.getName());
	        dto.setEnabled(chat.getEnabled());
	        dto.setCrtDate(chat.getCrtDate());
	        dto.setProject(chat.getProject());

	        chatDtoList.add(dto);
	    }
		
		return chatDtoList;
	} // getAllList
	
	@Override
	public List<Chat> getSearchList(ChatDTO dto) {	//검색 있는 전체 리스트
		log.debug("ChatServiceImpl -- getSearchList(()) invoked", dto);

		List<Chat> list = new Vector<>();
		log.debug("리포지토리 미 생성");
		
		return list;
	} // getSearchList
	
	@Override
	public ChatDTO createRoom(ChatDTO dto) {
	    log.debug("ChatServiceImpl -- createRoom({}) invoked", dto);

	    // 1. Chat 엔티티 생성 및 기본값 세팅
	    Chat chat = new Chat();
	    chat.setName(dto.getName());
	    chat.setProject(dto.getProject()); // dto에 Project 객체(id만 세팅된 상태로 전달됨)

	    // 2. 먼저 Chat 저장 (ID 생성됨)
	    Chat savedChat = chatRepository.save(chat);
	    List<ChatEmployeeDTO> chatEmployeeDTOList = new Vector<>();

	    // 3. 참여자 리스트 (ChatEmployeeDTO → ChatEmployee) 변환 및 저장
	    List<ChatEmployee> chatEmployeeList = new Vector<>();
	    
	    for (ChatEmployeeDTO chatEmpDTO : dto.getChatEmployees()) {
	        // 사원 조회
	        Employee emp = employeeRepository.findById(chatEmpDTO.getEmployee().getEmpno())
	            .orElseThrow(() -> new RuntimeException("사원 없음"));

	        // 복합키 생성
	        ChatEmployeePK pk = new ChatEmployeePK();
	        pk.setChatId(chat.getId());
	        pk.setEmpno(emp.getEmpno());

	        // ChatEmployee 생성 및 세팅
	        ChatEmployee chatEmployee = new ChatEmployee();
	        chatEmployee.setId(pk);
	        chatEmployee.setChat(savedChat);      // 연관관계 설정
	        chatEmployee.setEmployee(emp);   // 연관관계 설정

	        // 리스트에 추가
	        chatEmployeeList.add(chatEmployee);

	        // 저장
	        chatEmployeeRepository.save(chatEmployee);
	        
	        ChatEmployeeDTO chatEmployeeDTO = new ChatEmployeeDTO();
	        chatEmployeeDTO.setEmployee(emp);

	        chatEmployeeDTOList.add(chatEmployeeDTO);
	    }
	    
	    
	    ChatDTO result = new ChatDTO();
	    result.setId(savedChat.getId());
	    result.setName(savedChat.getName());
	    result.setProject(savedChat.getProject());
	    result.setCrtDate(savedChat.getCrtDate());
	    result.setEnabled(savedChat.getEnabled());
	    result.setChatEmployees(chatEmployeeDTOList);  // 참여자 리스트 세팅
	    
	    return result;
	}// createRoom
	
	@Override
	public ChatDTO getById(Long id) {	// 단일 조회
		log.debug("ChatServiceImpl -- getById({}) invoked", id);
		
		ChatDTO chatDTO = new ChatDTO();
		
		Chat selectedChat = this.chatRepository.findById(id).orElse(new Chat());	
		List<ChatEmployee> selectedChatEmployee = this.chatEmployeeRepository.findByIdChatId(id);
		
		chatDTO.setId(selectedChat.getId());
		chatDTO.setName(selectedChat.getName());
		List<Message> messages = messageRepository.findByChatId(id);
		chatDTO.setMessages(messages);
		chatDTO.setProject(selectedChat.getProject());
		
		List<ChatEmployeeDTO> chatEmployeeDTOList = new ArrayList<>();
	    
	    for (ChatEmployee chatEmployee : selectedChatEmployee) {
	        
	        ChatEmployeeDTO chatEmployeeDTO = new ChatEmployeeDTO();
	        
	        chatEmployeeDTO.setEmployee(chatEmployee.getEmployee());
	        chatEmployeeDTO.setChatId(id);
	        
	        chatEmployeeDTOList.add(chatEmployeeDTO);
	    }
	    chatDTO.setChatEmployees(chatEmployeeDTOList);
		
		return chatDTO;
	} // getById
	
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