package org.zerock.myapp.service;

import java.util.List;

import org.zerock.myapp.domain.ChatDTO;
import org.zerock.myapp.domain.ChatEmployeeDTO;
import org.zerock.myapp.entity.Chat;

public interface ChatService {
	
	public abstract List<Chat> findAllList();     			// 전체 조회
	public abstract List<Chat> getSearchList(ChatDTO dto); // 전체 조회(검색)
	
	public abstract Chat createRoom(ChatDTO dto);    	// 생성 처리
//	public abstract Chat createRoom(String name, List<ChatEmployee> ChatEmployees);
	public abstract Chat getById(Long id);    // 단일 조회
	public abstract Boolean update(ChatDTO dto);    	// 수정 처리
	public abstract Boolean deleteById(Long id);// 삭제 처리
//	public abstract Chat createRoom(String string, List<String> employeeIds);
	public abstract List<ChatEmployeeDTO> inviteEmployeesToChat(Long chatId, List<ChatEmployeeDTO> empnos);
	
}//end interface