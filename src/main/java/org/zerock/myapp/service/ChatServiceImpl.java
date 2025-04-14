package org.zerock.myapp.service;

import java.util.Date;
import java.util.List;
import java.util.Vector;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.zerock.myapp.domain.ChatDTO;
import org.zerock.myapp.entity.Chat;
import org.zerock.myapp.persistence.ChatRepository;

import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityNotFoundException;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@NoArgsConstructor

@Service
public class ChatServiceImpl implements ChatService {
    @Autowired ChatRepository chatRepository;
	
	
	@PostConstruct
    void postConstruct(){
        log.debug("ChatServiceImpl -- postConstruct() invoked");
        log.debug("chatRepository: {}", chatRepository);
    }//postConstruct


	@Override
	   public List<ChatDTO> findAllList() {   //검색 없는 전체 리스트
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
	public Chat createRoom(ChatDTO dto) {	//등록 처리
		log.debug("ChatServiceImpl -- create({}) invoked", dto);
		
		Chat chat = new Chat();
		
		chat.setName(dto.getName());
		chat.setEnabled(true);
		chat.setCrtDate(new Date());
//		chat.setProject(null);
		
		Chat saved = this.chatRepository.save(chat);
		
		log.debug("create data: {}", chat);
		
		return saved;
	} // create
	
	@Override
	public Chat getById(Long id) {	// 단일 조회
		log.debug("ChatServiceImpl -- getById({}) invoked", id);
		
		//값이 존재하면 반환하고, 없으면 new Course()와 같은 기본값을 반환합니다.
		Chat data = new Chat();//dao.findById(id).orElse(new Chat());
		
		data = this.chatRepository.findById(id).orElse(new Chat());
		
		return data;
	} // getById
	
	@Override
	public Boolean update(ChatDTO dto) {//수정 처리
		log.debug("ChatServiceImpl -- update({}) invoked", dto);

	    Chat chat = this.chatRepository.findById(dto.getId())
	        .orElseThrow(() -> new EntityNotFoundException("채팅방이 존재하지 않습니다."));

	    chat.setName(dto.getName());
	    chat.setUdtDate(new Date());
	    
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
	
	
	
}//end class
