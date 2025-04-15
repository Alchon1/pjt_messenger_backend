package org.zerock.myapp.service;

import java.util.List;

import org.zerock.myapp.domain.ChatDTO;
import org.zerock.myapp.entity.Chat;

public interface ChatService {
	
	public abstract List<Chat> findAllList();     			// 전체 조회
	public abstract List<Chat> getSearchList(ChatDTO dto); // 전체 조회(검색)
	public abstract Boolean createRoom(ChatDTO dto);    	// 생성 처리
	public abstract ChatDTO getById(Long id);    // 단일 조회
	public abstract Boolean update(ChatDTO dto);    	// 수정 처리
	public abstract Boolean deleteById(Long id,String empno);// 삭제 처리
	
}//end interface