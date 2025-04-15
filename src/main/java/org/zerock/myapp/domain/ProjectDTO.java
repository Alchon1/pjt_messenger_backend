package org.zerock.myapp.domain;

import java.util.Date;
import java.util.List;
import java.util.Vector;

import org.zerock.myapp.entity.Chat;
import org.zerock.myapp.entity.Employee;

import lombok.Data;


@Data
public class ProjectDTO {
	private Long 	id; 		// 프로젝트 id

	private String 	name; 		// 프로젝트명
	private String 	detail; 	// 프로젝트내용
	private Integer status; 	// 프로젝트상태 (진행예정=1, 진행중=2, 완료=3)
	private String 	startDate; 	// 시작일
	private String 	endDate; 	// 종료일
	private Boolean enabled = true; // 활성화상태(1=유효,0=삭제)

	private Date 	crtDate; // 등록일
	private Date 	udtDate; // 수정일

	// join
	private List<Chat> Chats = new Vector<>(); // 프로젝트뱃지
	private Employee pjtCreator; // 생성자 객체
	private Employee pjtManager; // 담당자 객체
	
	private String 	searchWord;	 // 검색항목
	private String 	searchText;  // 검색문
	
	private String 	creatorEmpno;// 생성자 id
	private String 	managerEmpno;// 담당자 id
	
} // end class
