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
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data

// 업무-사원 엔티티

@Entity
@Table(name="T_WORK_EMPLOYEE")
public class WorkEmployee implements Serializable {
	@Serial private static final long serialVersionUID = 1L;

	@EmbeddedId
	private WorkEmployeePK id; // 업무-사원 id

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
	@JoinColumn(name="EMPNO", insertable=false, updatable=false)
	private Employee Employee; // 사원 id

	@ManyToOne
	@JoinColumn(name="WORK_ID", referencedColumnName = "ID", insertable=false, updatable=false)
	private Work Work; // 업무 id

} // end class