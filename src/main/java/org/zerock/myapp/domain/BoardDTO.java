package org.zerock.myapp.domain;

import java.util.Date;

import org.zerock.myapp.entity.Employee;

import lombok.Data;


/**
 * 게시판(공지사항 + 건의사항) DTO
 */

@Data
public class BoardDTO {
	private Long id; // 게시판 id
	
	private String title; // 제목
	
	private Integer position; // 작성자의 직급(작성당시)(팀원=1, 팀장=2, 부서장=3, CEO=4, 인사담당자=5, 시스템관리자=9)
	
	private Integer count=0; // 조회수

	private String detail; // 내용
	
	private Integer type; // 유형(건의==1, 공지==2)

	private Boolean enabled = true; // 활성화상태(1=유효,0=삭제)

	private Date crtDate; // 생성일

	private Date udtDate; // 수정일

	
	// join
	private Employee employee; // 작성자
	
	
	private String searchWord; // 검색항목
	private String searchText; // 검색문

	private String 	authorEmpno;// 생성자 id
	
} // end class
