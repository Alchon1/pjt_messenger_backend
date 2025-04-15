package org.zerock.myapp.entity;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

import org.hibernate.annotations.CurrentTimestamp;
import org.hibernate.annotations.SourceType;
import org.hibernate.generator.EventType;
import org.zerock.myapp.util.BooleanToIntegerConverter;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.Data;


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

	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "seq_pjt")
	@SequenceGenerator(name = "seq_pjt", sequenceName = "T_PROJECT_SEQ", initialValue = 1, allocationSize = 1)
	@Column(name = "ID", unique=true, nullable=false)
	private Long id; // 프로젝트 id

	@Column(nullable=false, length = 500)
	private String name; // 프로젝트명

	@Column(nullable=true, length = 4000)
	private String detail; // 프로젝트내용

	@Column(nullable=false, length = 1)
	private Integer status; // 프로젝트상태 (진행예정=1, 진행중=2, 완료=3)


	@Column(nullable=false, length = 500)
	private LocalDate startDate; // 시작일
	
	@Column(nullable=false, length = 500)
	private LocalDate endDate; // 종료일

	@Convert(converter = BooleanToIntegerConverter.class)
	@Column(nullable=false, length = 1)
	private Boolean enabled = true; // 활성화상태(1=유효,0=삭제)

	
	@CurrentTimestamp(event = EventType.INSERT, source = SourceType.DB)
	@Basic(optional = false, fetch = FetchType.LAZY)
    private LocalDateTime crtDate; // 등록일

	@CurrentTimestamp(event = EventType.UPDATE, source = SourceType.DB)
	private LocalDateTime udtDate; // 수정일

	@Transient	// DB 컬럼으로 매핑되지 않음
	private Long endDday;	//마감 d-day


	
	// join
//	@ToString.Exclude
//	@OneToMany(mappedBy="Project")
//	private List<Chat> Chats = new Vector<>(); // 프로젝트뱃지

	@ManyToOne
	@JoinColumn(name="CREATOR")
	private Employee pjtCreator; // 생성자 ID

	@ManyToOne
	@JoinColumn(name="MANAGER")
	private Employee pjtManager; // 담당자 ID


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