package org.zerock.myapp.entity;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

import org.hibernate.annotations.CurrentTimestamp;
import org.hibernate.annotations.SourceType;
import org.hibernate.generator.EventType;
import org.zerock.myapp.util.BooleanToIntegerConverter;

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
import lombok.Data;


@Data

// 채팅방-사원 엔티티

@Entity
@Table(name="T_CHAT_EMPLOYEE")
public class ChatEmployee implements Serializable {
	@Serial private static final long serialVersionUID = 1L;

	@EmbeddedId
	private ChatEmployeePK id; // 채팅방-사원 id
	
	@Convert(converter = BooleanToIntegerConverter.class)
	@Column(nullable=false, length= 500)
	private Boolean enabled = true; // 활성화상태(1=유효,0=삭제)

	@CurrentTimestamp(event = EventType.INSERT, source = SourceType.DB)
   	@Basic(optional = false, fetch = FetchType.LAZY)
	private LocalDateTime crtDate; // 생성일

	@CurrentTimestamp(event = EventType.UPDATE, source = SourceType.DB)
	private LocalDateTime udtDate; // 수정일

	
	// join
	@ManyToOne
	@MapsId("chatId") // 복합 키의 chatId 필드와 매핑
	@JoinColumn(name="CHAT_ID", referencedColumnName="ID", insertable=false, updatable=false)
	private Chat Chat; // 채팅방 id

	@ManyToOne
	@MapsId("empno") // 복합 키의 empno 필드와 매핑
	@JoinColumn(name="EMPNO", insertable=false, updatable=false)
	private Employee Employee; // 사번

} // end class