package org.zerock.myapp.entity;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import org.hibernate.annotations.CurrentTimestamp;
import org.hibernate.annotations.SourceType;
import org.hibernate.generator.EventType;
import org.zerock.myapp.util.BooleanToIntegerConverter;

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

// 사원 entity

@Entity
@Table(name="T_EMPLOYEE")
public class Employee implements Serializable {
	@Serial private static final long serialVersionUID = 1L;

	//1. pk
	// 생성기 만들어야 함.
	@Id 
<<<<<<< HEAD
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "EMPNO", unique=true, nullable=false, length=255)
=======
 	@GeneratedValue(strategy = GenerationType.IDENTITY)
 	@Column(name = "EMPNO", unique=true, nullable=false, length=255)
>>>>>>> tae
	private String empno; // 사번
	
	@Column(nullable = false, length=255)
	private String name; // 사원명
	
	@Column(nullable = false, length=255)
	private Integer position; // 직급(팀원=1, 팀장=2, 부서장=3, CEO=4, 인사담당자=5, 시스템관리자=9)

	@Column(nullable = false, length=255)
	private String email; // 이메일

	@Column(nullable = false, length=255)
	private String loginId; // 아이디
	
	@Column(nullable = false, length=255)
	private String password; // 비밀번호

	@Column(nullable = false, length=255)
	private String tel; // 휴대폰번호(11자리)
	
	@Column(nullable = false, length=255)
	private String address; // 주소

	@Column(nullable = false)
	private Integer zipCode; // 우편번호
	
	@Convert(converter = BooleanToIntegerConverter.class)
	@Column(nullable=false)
	private Boolean enabled = true; // 활성화상태(1=유효,0=삭제)
	
	
	@CurrentTimestamp(event = EventType.INSERT, source = SourceType.DB)
	@Column(nullable=false)
	private Date crtDate; // 등록일

	@CurrentTimestamp(event = EventType.UPDATE, source = SourceType.DB)
	@Column
	private Date udtDate; // 수정일

	
//	// join
//	@ToString.Exclude
//	@OneToMany(mappedBy="Employee")
//	private List<Board> Board = new Vector<>(); // 게시판 작성자
<<<<<<< HEAD
//
//	@ToString.Exclude
//	@OneToMany(mappedBy="Employee")
//	private List<ChatEmployee> ChatEmployees = new Vector<>(); // 채팅방을 사용하는 사원
//
=======

//	@ToString.Exclude
//	@OneToMany(mappedBy="Employee")
//	private List<ChatEmployee> ChatEmployees = new Vector<>(); // 채팅방을 사용하는 사원

>>>>>>> tae
	@ManyToOne
	@JoinColumn(name="DEPT_ID")
	private Department Department; // 부서 ID
//
//	@ToString.Exclude
//	@OneToMany(mappedBy="Employee")
//	private List<File> Files = new Vector<>(); // 프로필사진
//
//	@ToString.Exclude
//	@OneToMany(mappedBy="Employee")
//	private List<Message> Messages = new Vector<>(); // 메시지 작성자

//	@ToString.Exclude
<<<<<<< HEAD
//	@OneToMany(mappedBy="pjtCreator")
//	private List<Project> createProjects = new Vector<>(); // pjt_만든사람 id

//	@ToString.Exclude
//	@OneToMany(mappedBy="pjtManager")
//	private List<Project> manageProjects = new Vector<>(); // pjt_담당자 id

//	@ToString.Exclude
=======
//	@OneToMany(mappedBy="Employee")
//	private List<File> Files = new Vector<>(); // 프로필사진
//
//	@ToString.Exclude
//	@OneToMany(mappedBy="Employee")
//	private List<Message> Messages = new Vector<>(); // 메시지 작성자

//	@ToString.Exclude
//	@OneToMany(mappedBy="pjtCreator")
//	private List<Project> createProjects = new Vector<>(); // pjt_만든사람 id

//	@ToString.Exclude
//	@OneToMany(mappedBy="pjtManager")
//	private List<Project> manageProjects = new Vector<>(); // pjt_담당자 id

//	@ToString.Exclude
>>>>>>> tae
//	@OneToMany(mappedBy="Employee")
//	private List<Work> Works = new Vector<>(); // 지시자
//
//	@ToString.Exclude
//	@OneToMany(mappedBy="Employee")
//	private List<WorkEmployee> WorkEmployees = new Vector<>(); // 담당자
<<<<<<< HEAD
//
//
=======


>>>>>>> tae
//	public Board addBoard(Board Board) {
//		getBoard().add(Board);
//		Board.setEmployee(this);
//
//		return Board;
//	} // addBoard
//
//	public Board removeBoard(Board Board) {
//		getBoard().remove(Board);
//		Board.setEmployee(null);
//
//		return Board;
//	} // removeBoard
<<<<<<< HEAD
//
//
=======


>>>>>>> tae
//	public ChatEmployee addChatEmployee(ChatEmployee ChatEmployee) {
//		getChatEmployees().add(ChatEmployee);
//		ChatEmployee.setEmployee(this);
//
//		return ChatEmployee;
//	} // addChatEmployee
//
//	public ChatEmployee removeChatEmployee(ChatEmployee ChatEmployee) {
//		getChatEmployees().remove(ChatEmployee);
//		ChatEmployee.setEmployee(null);
//
//		return ChatEmployee;
//	} // removeChatEmployee
<<<<<<< HEAD
//	
//
=======
	

>>>>>>> tae
//	public File addFile(File File) {
//		getFiles().add(File);
//		File.setEmployee(this);
//
//		return File;
//	} // addFile
//
//	public File removeFile(File File) {
//		getFiles().remove(File);
//		File.setEmployee(null);
//
//		return File;
//	} // removeFile
//
//	public Message addMessage(Message Message) {
//		getMessages().add(Message);
//		Message.setEmployee(this);
//
//		return Message;
//	} // addMessage
//
//	public Message removeMessage(Message Message) {
//		getMessages().remove(Message);
//		Message.setEmployee(null);
//
//		return Message;
//	} // removeMessage

	
//	public Project addCreateProjects(Project createProjects) {
//		getCreateProjects().add(createProjects);
//		createProjects.setPjtCreator(this);
//
//		return createProjects;
//	} // addCreateProjects
//
//	public Project removeCreateProjects(Project createProjects) {
//		getCreateProjects().remove(createProjects);
//		createProjects.setPjtCreator(null);
//
//		return createProjects;
//	} // removeCreateProjects
//
//
//	public Project addManageProjects(Project manageProjects) {
//		getManageProjects().add(manageProjects);
//		manageProjects.setPjtManager(this);
//
//		return manageProjects;
//	} // addManageProjects
//
//	public Project removeManageProjects(Project manageProjects) {
//		getManageProjects().remove(manageProjects);
//		manageProjects.setPjtManager(null);
//
//		return manageProjects;
//	} // removeManageProjects


//	public Work addWork(Work Work) {
//		getWorks().add(Work);
//		Work.setEmployee(this);
//
//		return Work;
//	} // addWork
//
//	public Work removeWork(Work Work) {
//		getWorks().remove(Work);
//		Work.setEmployee(null);
//
//		return Work;
//	} // removeWork
//
//
//	public WorkEmployee addWorkEmployee(WorkEmployee WorkEmployee) {
//		getWorkEmployees().add(WorkEmployee);
//		WorkEmployee.setEmployee(this);
//
//		return WorkEmployee;
//	} // addWorkEmployee
//
//	public WorkEmployee removeWorkEmployee(WorkEmployee WorkEmployee) {
//		getWorkEmployees().remove(WorkEmployee);
//		WorkEmployee.setEmployee(null);
//
//		return WorkEmployee;
//	} // removeWorkEmployee

} // end class