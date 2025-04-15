package org.zerock.myapp.domain;

import java.util.Date;
import java.util.List;
import java.util.Vector;

import org.zerock.myapp.entity.Employee;
import org.zerock.myapp.entity.WorkEmployee;

import lombok.Builder;
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
	private Integer status; // 업무상태(진행예정=1, 진행중=2, 완료대기=3, 완료=4)
	private Integer type; // 업무분류(개발=1, 운영=2, 인사=3, 회계=4, 마케팅=5)
	private String startDate; // 시작일
	private String endDate; // 종료일
	private Boolean enabled = true; // 활성화상태(1=유효,0=삭제)
	
	// join
	private Employee employee; // 지시자 id
	private List<WorkEmployee> workEmployees = new Vector<>(); // 업무-사원 테이블
	
	// 요청업무 or 담당업무
	private String work;
	
} // end class
