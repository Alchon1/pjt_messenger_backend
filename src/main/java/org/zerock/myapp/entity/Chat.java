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

// 채팅방 entity

@Entity
@Table(name="T_CHAT")
public class Chat implements Serializable {
	@Serial private static final long serialVersionUID = 1L;

	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID", unique=true, nullable=false)
	private Long id; // 채팅방 id

	@Column(nullable=false)
	private String name; // 채팅방명

	@Convert(converter = BooleanToIntegerConverter.class)
	@Column(nullable=false)
	private Boolean enabled = true; // 활성화상태(1=유효,0=삭제)

	
	@CurrentTimestamp(event = EventType.INSERT, source = SourceType.DB)
	@Column(name="CRT_DATE", nullable = false)
	private Date crtDate; // 생성일

	@CurrentTimestamp(event = EventType.UPDATE, source = SourceType.DB)
	@Column(name="UDT_DATE")
	private Date udtDate; // 수정일

	// join
	@ManyToOne
	@JoinColumn(name="PJT_ID")
	private Project Project; // 프로젝트 뱃지 id

//	@ToString.Exclude
//	@OneToMany(mappedBy="Chat")
//	private List<ChatEmployee> ChatEmployees = new Vector<>(); //  작성자 id 
//
//	@ToString.Exclude
//	@OneToMany(mappedBy="Chat")
//	private List<Message> Messages = new Vector<>(); // 메시지 id

	
//	public ChatEmployee addChatEmployee(ChatEmployee ChatEmployee) {
//		getChatEmployees().add(ChatEmployee);
//		ChatEmployee.setChat(this);
//
//		return ChatEmployee;
//	} // addChatEmployee
//
//	public ChatEmployee removeChatEmployee(ChatEmployee ChatEmployee) {
//		getChatEmployees().remove(ChatEmployee);
//		ChatEmployee.setChat(null);
//
//		return ChatEmployee;
//	} // removeChatEmployee
//
//	public Message addMessage(Message Message) {
//		getMessages().add(Message);
//		Message.setChat(this);
//
//		return Message;
//	} // addMessage
//
//	public Message removeMessage(Message Message) {
//		getMessages().remove(Message);
//		Message.setChat(null);
//
//		return Message;
//	} // removeMessage

} // end class