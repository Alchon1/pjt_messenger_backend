package org.zerock.myapp.domain;

import java.util.Date;
import java.util.List;
import java.util.Vector;

import org.zerock.myapp.entity.Employee;
import org.zerock.myapp.entity.WorkEmployee;

import lombok.Data;


/**
 * 업무 DTO
 */

@Data
public class WorkDTO {
	private Long id; // 업무 id
	
	private String name; // 업무명
	private String detail; // 업무내용
	private String memo; // 업무 메모
	private Integer status; // 업무상태(진행예정, 진행중, 완료대기, 완료)
	private Integer type; // 업무분류(개발, 운영, 인사, 회계, 마케팅)
	private String startDate; // 시작일
	private String endDate; // 종료일
	private Boolean enabled = true; // 활성화상태(1=유효,0=삭제)
	
	private Date crtDate; // 생성일
	private Date udtDate; // 수정일
	
	// join
	private Employee Employee; // 지시자 id
	private List<WorkEmployee> WorkEmployees = new Vector<>(); // 업무-사원 테이블
	
	private String searchWord; // 검색항목
	private String searchText; // 검색문
	
} // end class
