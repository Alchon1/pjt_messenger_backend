package org.zerock.myapp.service;

import java.util.List;

import org.zerock.myapp.domain.MessageDTO;
import org.zerock.myapp.entity.Message;

public interface MessageService {
	
	public abstract List<Message> getAllList();     			// 전체 조회
	public abstract List<Message> getSearchList(MessageDTO dto); // 전체 조회(검색)

	public abstract Message create(MessageDTO dto);    	// 생성 처리
	public abstract Message getById(String id);    // 단일 조회
	public abstract Boolean update(MessageDTO dto);    	// 수정 처리
	public abstract Boolean deleteById(String id);// 삭제 처리
	
	public abstract Message saveMessage(MessageDTO dto);
	
}//end interface