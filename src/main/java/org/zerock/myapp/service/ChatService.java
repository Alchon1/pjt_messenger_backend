package org.zerock.myapp.service;

import java.util.List;

import org.zerock.myapp.domain.ChatDTO;
import org.zerock.myapp.entity.Chat;

public interface ChatService {
	
	public abstract List<Chat> getAllList();     			// 전체 조회
	public abstract List<Chat> getSearchList(ChatDTO dto); // 전체 조회(검색)
	
	public abstract Chat create(ChatDTO dto);    	// 생성 처리
	public abstract Chat getById(String id);    // 단일 조회
	public abstract Boolean update(ChatDTO dto);    	// 수정 처리
	public abstract Boolean deleteById(String id);// 삭제 처리
	
}//end interface