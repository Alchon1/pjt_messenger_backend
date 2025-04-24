package org.zerock.myapp.entity;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

import org.hibernate.annotations.CurrentTimestamp;
import org.hibernate.annotations.SourceType;
import org.hibernate.generator.EventType;
import org.zerock.myapp.util.BooleanToIntegerConverter;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;


@Data

// 사원 entity

@Entity
@Table(name="T_EMPLOYEE")
public class Employee implements Serializable {
	@Serial private static final long serialVersionUID = 1L;

	//1. pk
	@Id 
	@Column(name = "EMPNO", unique=true, nullable=false, length=255)
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

	
	@ManyToOne
	@JoinColumn(name="DEPT_ID")
	private Department department; // 부서 ID


} // end class