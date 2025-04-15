package org.zerock.myapp.service;

import java.util.List;
import java.util.Vector;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.zerock.myapp.domain.ChatDTO;
import org.zerock.myapp.entity.Chat;
import org.zerock.myapp.persistence.ChatRepository;

import jakarta.annotation.PostConstruct;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@NoArgsConstructor

@Service
public class ChatServiceImpl implements ChatService {
    @Autowired ChatRepository dao;
	
	
	@PostConstruct
    void postConstruct(){
        log.debug("ChatServiceImpl -- postConstruct() invoked");
        log.debug("dao: {}", dao);
    }//postConstruct


	@Override
	public List<Chat> getAllList() {	//검색 없는 전체 리스트
		log.debug("ChatServiceImpl -- getAllList() invoked");
		
		List<Chat> list = dao.findAll();
		
		return list;
	} // getAllList
	
	@Override
	public List<Chat> getSearchList(ChatDTO dto) {	//검색 있는 전체 리스트
		log.debug("ChatServiceImpl -- getSearchList(()) invoked", dto);

		List<Chat> list = new Vector<>();
		log.debug("리포지토리 미 생성");
		
		return list;
	} // getSearchList
	
	@Override
	public Chat create(ChatDTO dto) {	//등록 처리
		log.debug("ChatServiceImpl -- create({}) invoked", dto);
		
		Chat data = new Chat();//dao.save(dto);
		log.debug("create data: {}", data);
		
		return data;
	} // create
	
	@Override
	public Chat getById(String id) {	// 단일 조회
		log.debug("ChatServiceImpl -- getById({}) invoked", id);
		
		//값이 존재하면 반환하고, 없으면 new Course()와 같은 기본값을 반환합니다.
		Chat data = new Chat();//dao.findById(id).orElse(new Chat());
		
		return data;
	} // getById
	
	@Override
	public Boolean update(ChatDTO dto) {//수정 처리
		log.debug("ChatServiceImpl -- update({}) invoked", dto);
		
//		Chat data = dao.save(dto);
//		log.debug("create data: {}", data);
		Boolean isUpdate = true;
		return isUpdate;
	} // update

	@Override
	public Boolean deleteById(String id) { // 삭제 처리
		log.debug("ChatServiceImpl -- deleteById({}) invoked", id);
		
		//dao.deleteById(id);
		return true;
	} // deleteById
	
	
	
}//end class
