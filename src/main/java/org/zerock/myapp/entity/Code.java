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
import jakarta.persistence.Table;
import lombok.Data;


@Data

// 코드 entity

@Entity
@Table(name="T_CODE")
public class Code implements Serializable {
	@Serial private static final long serialVersionUID = 1L;

	@EmbeddedId
	private CodePK id; // 코드 ID
	
	@Column(nullable=false, length = 255)
	private String name; // 이름

	@Column(nullable=false, length = 255)
	private String description; // 설명

	@Convert(converter = BooleanToIntegerConverter.class)
	@Column(nullable=false)
	private Boolean enabled = true; // 활성화상태(1=유효,0=삭제)

	@Column(nullable = false)
	private Integer orderNum;
	
	
	@CurrentTimestamp(event = EventType.INSERT, source = SourceType.DB)
	@Column(nullable = false)
	private Date crtDate;

	@CurrentTimestamp(event = EventType.UPDATE, source = SourceType.DB)
	@Column
	private Date udtDate;
	
} // end class