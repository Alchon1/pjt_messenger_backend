package org.zerock.myapp.entity;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

import org.hibernate.annotations.CurrentTimestamp;
import org.hibernate.annotations.SourceType;
import org.hibernate.generator.EventType;
import org.zerock.myapp.util.BooleanToIntegerConverter;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.Data;


@Data

// 채팅방-사원 엔티티

@Entity
@Table(name="T_CHAT_EMPLOYEE")
public class ChatEmployee implements Serializable {
	@Serial private static final long serialVersionUID = 1L;

	@EmbeddedId // 복합키를 나타내는 어노테이션.
	private ChatEmployeePK id; // 채팅방-사원 id
	
	@Convert(converter = BooleanToIntegerConverter.class)
	@Column(nullable=false, length= 500)
	private Boolean enabled = true; // 활성화상태(1=유효,0=삭제)

	@CurrentTimestamp(event = EventType.INSERT, source = SourceType.DB)
   	@Basic(optional = false, fetch = FetchType.LAZY)
	private Date crtDate; // 생성일

	@CurrentTimestamp(event = EventType.UPDATE, source = SourceType.DB)
	private Date udtDate; // 수정일

	@Column(nullable=true, length= 5)
	private Integer cnt; // 채팅방 안에 있는 인원 수
	
	// join
	@ManyToOne
	@MapsId("chatId") // 복합 키의 chatId 필드와 매핑
	@JoinColumn(name="CHAT_ID", referencedColumnName="ID", insertable=false, updatable=false)
	private Chat Chat; // 채팅방 id

	@ManyToOne
	@MapsId("empno") // 복합 키의 empno 필드와 매핑
	@JoinColumn(name="EMPNO", insertable=false, updatable=false)
	private Employee Employee; // 사번

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss", timezone = "Asia/Seoul")
	@Transient	// DB 컬럼으로 매핑되지 않음
	private Date lastMsg;	//마지막 메시지
	
} // end class