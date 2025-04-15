package org.zerock.myapp.service;

import java.util.Date;
import java.util.List;
import java.util.Vector;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.zerock.myapp.domain.MessageDTO;
import org.zerock.myapp.entity.Message;
import org.zerock.myapp.persistence.ChatRepository;
import org.zerock.myapp.persistence.EmployeeRepository;
import org.zerock.myapp.persistence.MessageRepository;

import jakarta.annotation.PostConstruct;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@NoArgsConstructor

@Service
public class MessageServiceImpl implements MessageService {

    @Autowired MessageRepository messageRepository;
    @Autowired private ChatRepository chatRepository;
	@Autowired private EmployeeRepository employeeRepository;
    

	@PostConstruct
    void postConstruct(){
        log.debug("MessageServiceImpl -- postConstruct() invoked");
        log.debug("dao: {}", messageRepository);
    }//postConstruct


	@Override
	public List<Message> getAllList() {	//검색 없는 전체 리스트
		log.debug("MessageServiceImpl -- getAllList() invoked");
		
		List<Message> list = messageRepository.findAll();
		
		return list;
	} // getAllList
	
	@Override
	public List<Message> getSearchList(MessageDTO dto) {	//검색 있는 전체 리스트
		log.debug("MessageServiceImpl -- getSearchList(()) invoked", dto);

		List<Message> list = new Vector<>();
		log.debug("리포지토리 미 생성");
		
		return list;
	} // getSearchList
	
	@Override
	public Message create(MessageDTO dto) {	//등록 처리
		log.debug("MessageServiceImpl -- create({}) invoked", dto);
		
		Message data = new Message();//dao.save(dto);
		log.debug("create data: {}", data);
		
		return data;
	} // create
	
	@Override
	public Message getById(String id) {	// 단일 조회
		log.debug("MessageServiceImpl -- getById({}) invoked", id);
		
		//값이 존재하면 반환하고, 없으면 new Course()와 같은 기본값을 반환합니다.
		Message data = new Message();//dao.findById(id).orElse(new Message());
		
		return data;
	} // getById
	
	@Override
	public Boolean update(MessageDTO dto) {//수정 처리
		log.debug("MessageServiceImpl -- update({}) invoked", dto);
		
//		Message data = dao.save(dto);
//		log.debug("create data: {}", data);
		Boolean isUpdate = true;
		return isUpdate;
	} // update

	@Override
	public Boolean deleteById(String id) { // 삭제 처리
		log.debug("MessageServiceImpl -- deleteById({}) invoked", id);
		
		//dao.deleteById(id);
		return true;
	} // deleteById


	@Override
	public Message saveMessage(MessageDTO dto) {
		
		Message message = new Message();


		message.setEmployee(employeeRepository.findById(dto.getEmployee().getEmpno()).orElseThrow());
		message.setDetail(dto.getDetail());
		message.setChat(chatRepository.findById(dto.getChat().getId())
                .orElseThrow(() -> new IllegalArgumentException("Chat not found")));
//		message.setChat(dto.getChat()); / 어떤 방법이 맞는지 궁금
		message.setCrtDate(new Date());
		
		return this.messageRepository.save(message);

	} // saveMessage
	
	
	
}//end class
