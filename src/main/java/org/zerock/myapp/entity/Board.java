package org.zerock.myapp.entity;

import java.io.Serial;
import java.io.Serializable;
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
import lombok.Data;


@Data

//JSON 으로 변환해서 보낼때, 제외 할 항목
@JsonIgnoreProperties({
	"udtDate"
})

// 게시판 entity

@Entity
@Table(name="T_BOARD")
public class Board implements Serializable {
	@Serial private static final long serialVersionUID = 1L;

	//1. pk
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "ko")
	@SequenceGenerator(name = "ko", sequenceName = "T_PROJECT_SEQ", initialValue = 1, allocationSize = 1)
	@Column(name = "ID", unique=true, nullable=false)
	private Long id; // 게시판 id
	
	@Column(nullable=false, length = 500)
	private String title; // 제목
	
	@Column(nullable=false, length = 1)
	private Integer position; // 작성자의 직급(작성당시)(팀원=1, 팀장=2, 부서장=3, CEO=4, 인사담당자=5, 시스템관리자=9)
	
	@Column(nullable=false)
	private Integer count=0; // 조회수

	@Column(nullable=true, length = 4000)
	private String detail; // 내용
	
	@Column(nullable=false, length = 1)
	private Integer type; // 유형(건의==1, 공지==2)

	@Convert(converter = BooleanToIntegerConverter.class)
	@Column(nullable=false, length = 1)
	private Boolean enabled = true; // 활성화상태(1=유효,0=삭제)

	
	@CurrentTimestamp(event = EventType.INSERT, source = SourceType.DB)
	@Basic(optional = false, fetch = FetchType.LAZY)
    private LocalDateTime crtDate; // 등록일

	@CurrentTimestamp(event = EventType.UPDATE, source = SourceType.DB)
	@Column
	private LocalDateTime udtDate; // 수정일

	
	// join
	@ManyToOne
	@JoinColumn(name="EMPNO")
	private Employee Employee; // 작성자

} // end class