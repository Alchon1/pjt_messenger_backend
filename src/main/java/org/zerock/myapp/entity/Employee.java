package org.zerock.myapp.entity;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import org.hibernate.annotations.CurrentTimestamp;
import org.hibernate.annotations.SourceType;
import org.hibernate.generator.EventType;
import org.zerock.myapp.util.BooleanToIntegerConverter;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.ToString;


@Data

//JSON 으로 변환해서 보낼때, 제외 할 항목
@JsonIgnoreProperties({
	"udtDate"
})

// 사원 entity

@Entity
@Table(name="T_EMPLOYEE")
public class Employee implements Serializable {
	@Serial private static final long serialVersionUID = 1L;

	//1. pk
	// 생성기 만들어야 함.
	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "EMPNO", unique=true, nullable=false)
	private String empno; // 사번
	
	@Column(nullable = false)
	private String name; // 사원명
	
	@Column(nullable = false)
	private Integer position; // 직급(팀원=1, 팀장=2, 부서장=3, CEO=4, 인사담당자=5, 시스템관리자=9)

	@Column(nullable = false)
	private String email; // 이메일

	@Column(nullable = false)
	private String loginId; // 아이디
	
	@Column(nullable = false)
	private String password; // 비밀번호

	@Column(nullable = false)
	private String tel; // 휴대폰번호(11자리)
	
	@Column(nullable = false)
	private String address; // 주소

	@Column(nullable = false)
	private BigDecimal zipCode; // 우편번호
	
	@Convert(converter = BooleanToIntegerConverter.class)
	@Column(nullable=false)
	private Boolean enabled = true; // 활성화상태(1=유효,0=삭제)
	
	
	// 사번 생성 ( 테스트중 )
//	private String employeeCode;
//	private LocalDate joinDate; // date : 날짜 + 시간 / localDate : 날짜만 다룸 ( java8 + )
//	private String role; // E = 사원 , C=  CEO, H = 인사담당자, A = 시스템관리자
	
	
	
	
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
	@CurrentTimestamp(event = EventType.INSERT, source = SourceType.DB)
	@Column(name="CRT_DATE")
	private Date crtDate; // 등록일

	@CurrentTimestamp(event = EventType.UPDATE, source = SourceType.DB)
	@Column(name="UDT_DATE")
	private Date udtDate; // 수정일

	
	// join
	@ToString.Exclude
	@OneToMany(mappedBy="Employee")
	private List<Board> Board = new Vector(); // 게시판 작성자

	@ToString.Exclude
	@OneToMany(mappedBy="Employee")
	private List<ChatEmployee> ChatEmployees = new Vector(); // 채팅방을 사용하는 사원

	@ManyToOne
	@JoinColumn(name="DEPT_ID")
	private Department Department; // 부서 ID

	@ToString.Exclude
	@OneToMany(mappedBy="Employee")
	private List<File> Files = new Vector(); // 프로필사진

	@ToString.Exclude
	@OneToMany(mappedBy="Employee")
	private List<Message> Messages = new Vector(); // 메시지 작성자

	@ToString.Exclude
	@OneToMany(mappedBy="Employee1")
	private List<Project> Projects1 = new Vector(); // 만든사람 id

	@ToString.Exclude
	@OneToMany(mappedBy="Employee2")
	private List<Project> Projects2 = new Vector(); // 담당자 id

	@ToString.Exclude
	@OneToMany(mappedBy="Employee")
	private List<Work> Works = new Vector(); // 지시자

	@ToString.Exclude
	@OneToMany(mappedBy="Employee")
	private List<WorkEmployee> WorkEmployees = new Vector(); // 담당자


	public Board addBoard(Board Board) {
		getBoard().add(Board);
		Board.setEmployee(this);

		return Board;
	} // addBoard

	public Board removeBoard(Board Board) {
		getBoard().remove(Board);
		Board.setEmployee(null);

		return Board;
	} // removeBoard


	public ChatEmployee addChatEmployee(ChatEmployee ChatEmployee) {
		getChatEmployees().add(ChatEmployee);
		ChatEmployee.setEmployee(this);

		return ChatEmployee;
	} // addChatEmployee

	public ChatEmployee removeChatEmployee(ChatEmployee ChatEmployee) {
		getChatEmployees().remove(ChatEmployee);
		ChatEmployee.setEmployee(null);

		return ChatEmployee;
	} // removeChatEmployee
	

	public File addFile(File File) {
		getFiles().add(File);
		File.setEmployee(this);

		return File;
	} // addFile

	public File removeFile(File File) {
		getFiles().remove(File);
		File.setEmployee(null);

		return File;
	} // removeFile

	public Message addMessage(Message Message) {
		getMessages().add(Message);
		Message.setEmployee(this);

		return Message;
	} // addMessage

	public Message removeMessage(Message Message) {
		getMessages().remove(Message);
		Message.setEmployee(null);

		return Message;
	} // removeMessage

	
	public Project addProjects1(Project Projects1) {
		getProjects1().add(Projects1);
		Projects1.setEmployee1(this);

		return Projects1;
	} // addProjects1

	public Project removeProjects1(Project Projects1) {
		getProjects1().remove(Projects1);
		Projects1.setEmployee1(null);

		return Projects1;
	} // removeProjects1


	public Project addProjects2(Project Projects2) {
		getProjects2().add(Projects2);
		Projects2.setEmployee2(this);

		return Projects2;
	} // addProjects2

	public Project removeProjects2(Project Projects2) {
		getProjects2().remove(Projects2);
		Projects2.setEmployee2(null);

		return Projects2;
	} // removeProjects2


	public Work addWork(Work Work) {
		getWorks().add(Work);
		Work.setEmployee(this);

		return Work;
	} // addWork

	public Work removeWork(Work Work) {
		getWorks().remove(Work);
		Work.setEmployee(null);

		return Work;
	} // removeWork


	public WorkEmployee addWorkEmployee(WorkEmployee WorkEmployee) {
		getWorkEmployees().add(WorkEmployee);
		WorkEmployee.setEmployee(this);

		return WorkEmployee;
	} // addWorkEmployee

	public WorkEmployee removeWorkEmployee(WorkEmployee WorkEmployee) {
		getWorkEmployees().remove(WorkEmployee);
		WorkEmployee.setEmployee(null);

		return WorkEmployee;
	} // removeWorkEmployee

} // end class