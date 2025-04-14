package org.zerock.myapp.entity;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import org.hibernate.annotations.CurrentTimestamp;
import org.hibernate.annotations.SourceType;
import org.hibernate.generator.EventType;
import org.zerock.myapp.util.BooleanToIntegerConverter;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

// 프로젝트 entity

@Entity
@Table(name="T_PROJECT")
public class Project implements Serializable {
	@Serial private static final long serialVersionUID = 1L;

	//1. pk
	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID", unique=true, nullable=false)
	private Long id; // 프로젝트 id

	@Column(nullable=false)
	private String name; // 프로젝트명

	@Column(nullable=true)
	private String detail; // 프로젝트내용

	@Column(nullable=false)
	private Integer status; // 프로젝트상태 (진행예정=1, 진행중=2, 완료=3)

	@Column(nullable=false)
	private String startDate; // 시작일
	
	@Column(nullable=false)
	private String endDate; // 종료일

	@Convert(converter = BooleanToIntegerConverter.class)
	@Column(nullable=false)
	private Boolean enabled = true; // 활성화상태(1=유효,0=삭제)

	
	@CurrentTimestamp(event = EventType.INSERT, source = SourceType.DB)
	@Column(name="CRT_DATE", nullable = false)
	private Date crtDate; // 등록일

	@CurrentTimestamp(event = EventType.UPDATE, source = SourceType.DB)
	@Column(name="UDT_DATE")
	private Date udtDate; // 수정일

	
	// join
//	@ToString.Exclude
//	@OneToMany(mappedBy="Project")
//	private List<Chat> Chats = new Vector<>(); // 프로젝트뱃지
//	
	@ToString.Exclude
	@ManyToOne
	@JoinColumn(name="CREATOR")
	private Employee Employee1; // 생성자 ID
	
	@ToString.Exclude
	@ManyToOne
	@JoinColumn(name="MANAGER")
	private Employee Employee2; // 담당자 ID


//	public Chat addChat(Chat Chat) {
//		getChats().add(Chat);
//		Chat.setProject(this);
//
//		return Chat;
//	} // addChat
//
//	public Chat removeChat(Chat Chat) {
//		getChats().remove(Chat);
//		Chat.setProject(null);
//
//		return Chat;
//	} // removeChat

} // end class