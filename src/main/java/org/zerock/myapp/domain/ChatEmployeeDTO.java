package org.zerock.myapp.domain;

import org.zerock.myapp.entity.Employee;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ChatEmployeeDTO {
//	private String empno;
	private Long chatId;
	private Employee employee;
}
