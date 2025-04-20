package org.zerock.myapp.entity;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

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
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Data;


@Data



// 파일 엔티티

@Entity
@Table(name="T_FILE")
public class File implements Serializable {
	@Serial private static final long serialVersionUID = 1L;

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "ko")
	@SequenceGenerator(name = "ko", sequenceName = "T_FILE_SEQ", initialValue = 1, allocationSize = 1)
	@Column(name = "ID", unique=true, nullable=false)
	private Long id; // 파일 id

	@Column(nullable=false, length = 255)
	private String original; // 원본명(확장자포함)
	
	@Column(nullable=false, length = 255)
	private String uuid; // UUID명
	
	@Column(nullable=false, length = 255)
	private String path; // 파일경로

	@Convert(converter = BooleanToIntegerConverter.class)
	@Column(nullable=false)
	private Boolean enabled = true; // 활성화상태(1=유효,0=삭제)

	
	@CurrentTimestamp(event = EventType.INSERT, source = SourceType.DB)
	@Column(nullable = false)
	private Date crtDate; // 생성일

	@CurrentTimestamp(event = EventType.UPDATE, source = SourceType.DB)
	@Column
	private Date udtDate; // 수정일


	// join
	@ManyToOne
	@JoinColumn(name="EMPNO")
	private Employee Employee; // 사원id

	@ManyToOne
	@JoinColumn(name="MESSAGE_ID")
	private Message Message; // 메시지id

} // end class