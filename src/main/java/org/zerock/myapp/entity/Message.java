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

// 채팅메시지 entity

@Entity
@Table(name="T_MESSAGE")
public class Message implements Serializable {
	@Serial private static final long serialVersionUID = 1L;

	//1. pk
	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID", unique=true, nullable=false)
	private Long id; // 메시지 id

	// 2-1. General Properties
	@Column(nullable=true)
	private String detail; // 내용

	@Convert(converter = BooleanToIntegerConverter.class)
	@Column(nullable=false)
	private Boolean enabled = true; // 활성화상태(1=유효,0=삭제)
	
	
	@CurrentTimestamp(event = EventType.INSERT, source = SourceType.DB)
	@Column(name="CRT_DATE", nullable=false)
	private Date crtDate; // 생성일

	@CurrentTimestamp(event = EventType.UPDATE, source = SourceType.DB)
	@Column(name="UDT_DATE")
	private Date udtDate; // 수정일

	
	// join
	// @JsonManagedReference("course-instructor")	// fix
//	@ToString.Exclude
//	@OneToMany(mappedBy="Message")
//	private List<File> Files = new Vector<>(); // 파일

	@ManyToOne
	@JoinColumn(name="CHAT_ID")
	private Chat Chat; // 채팅

	@ManyToOne
	@JoinColumn(name="EMPNO")
	private Employee Employee; // 사원


//	public File addTFile(File File) {
//		getFiles().add(File);
//		File.setMessage(this);
//
//		return File;
//	} // addFile
//
//	public File removeFile(File File) {
//		getFiles().remove(File);
//		File.setMessage(null);
//
//		return File;
//	} // removeFile

} // end class