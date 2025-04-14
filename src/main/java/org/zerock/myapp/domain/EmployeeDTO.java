package org.zerock.myapp.domain;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import org.zerock.myapp.entity.Board;
import org.zerock.myapp.entity.ChatEmployee;
import org.zerock.myapp.entity.Department;
import org.zerock.myapp.entity.File;
import org.zerock.myapp.entity.Message;
import org.zerock.myapp.entity.Project;
import org.zerock.myapp.entity.Work;
import org.zerock.myapp.entity.WorkEmployee;

import lombok.Data;


/**
 * 회원 DTO
 */

@Data
public class EmployeeDTO {
private String empno; // 사번
	
	private String name; // 사원명
	private Integer position; // 직급(팀원=1, 팀장=2, 부서장=3, CEO=4, 인사담당자=5, 시스템관리자=9)
	private String email; // 이메일
	private String loginId; // 아이디
	private String password; // 비밀번호
	private String tel; // 휴대폰번호(11자리)
	private String address; // 주소
	private BigDecimal zipCode; // 우편번호
	private Boolean enabled = true; // 활성화상태(1=유효,0=삭제)
	
	private Date crtDate; // 등록일
	private Date udtDate; // 수정일
	
	// join
	private List<Board> Board = new Vector<>(); // 게시판 작성자
	private List<ChatEmployee> ChatEmployees = new Vector<>(); // 채팅방을 사용하는 사원
	private Department Department; // 부서 ID
	private List<File> Files = new Vector<>(); // 프로필사진
	private List<Message> Messages = new Vector<>(); // 메시지 작성자
//	private List<Project> Projects1 = new Vector<>(); // 만든사람 id
//	private List<Project> Projects2 = new Vector<>(); // 담당자 id
	private List<Work> Works = new Vector<>(); // 지시자
	private List<WorkEmployee> WorkEmployees = new Vector<>(); // 담당자
	
	private String searchWord; // 검색항목
	private String searchText; // 검색문
	
} // end class
