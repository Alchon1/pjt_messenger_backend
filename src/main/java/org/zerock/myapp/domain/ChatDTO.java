package org.zerock.myapp.domain;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Vector;

import org.zerock.myapp.entity.Message;
import org.zerock.myapp.entity.Project;

import lombok.Data;


/**
 * 채팅 DTO
 */

@Data
public class ChatDTO {
	private Long id; // 채팅방 id

	private String name; // 채팅방명
	private Boolean enabled = true; // 활성화상태(1=유효,0=삭제)

	private LocalDateTime crtDate; // 생성일
	private LocalDateTime udtDate; // 수정일

	// join
	private Project Project; // 프로젝트 뱃지 id
	private List<ChatEmployeeDTO> ChatEmployees; //  작성자 id 
	private List<Message> Messages = new Vector<>(); // 메시지 id
	
	private String searchWord; // 검색 항목
	private String searchText; // 검색문
	
} // end class
