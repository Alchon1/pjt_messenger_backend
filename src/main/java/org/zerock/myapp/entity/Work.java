package org.zerock.myapp.entity;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

import org.hibernate.annotations.CurrentTimestamp;
import org.hibernate.annotations.SourceType;
import org.hibernate.generator.EventType;
import org.zerock.myapp.domain.WorkDTO;
import org.zerock.myapp.util.BooleanToIntegerConverter;

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
import lombok.Builder;
import lombok.Data;


@Builder
@Data

// 업무 entity

@Entity
@Table(name="T_WORK")
public class Work implements Serializable {
	@Serial private static final long serialVersionUID = 1L;

	//1. pk
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "ko")
	@SequenceGenerator(name = "ko", sequenceName = "T_WORK_SEQ", initialValue = 1, allocationSize = 1)
	@Column(name = "ID", unique=true, nullable=false)
	private Long id; // 업무 id
	
	@Column(nullable=false, length = 255)
	private String name; // 업무명

	@Column(nullable=true, length = 4000)
	private String detail; // 업무내용
	
	@Column(nullable=true, length = 4000)
	private String memo; // 업무 메모

	@Column(nullable=false)
	private Integer status; // 업무상태(진행예정=1, 진행중=2, 완료대기=3, 완료=4)

	@Column(nullable=false)
	private Integer type; // 업무분류(개발=1, 운영=2, 인사=3, 회계=4, 마케팅=5)

	@Column(nullable=false)
	private Date startDate; // 시작일
	
	@Column(nullable=false)
	private Date endDate; // 종료일
	
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
	private Employee employee; // 요청자 id

	public static Work toEntity(WorkDTO dto) { // DTO -> 엔티티 편의메소드
		return Work.builder()
				.id(dto.getId())
				.name(dto.getName())
				.detail(dto.getDetail())
				.memo(dto.getMemo())
				.status(dto.getStatus())
				.type(dto.getType())
				.startDate(dto.getStartDate())
				.endDate(dto.getEndDate())
				.enabled(dto.getEnabled())
				.employee(dto.getEmployee())
				.build();
	} // toEntity

} // end class