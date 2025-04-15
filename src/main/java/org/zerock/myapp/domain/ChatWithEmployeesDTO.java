package org.zerock.myapp.domain;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

// 채팅방 단일 조회 시 사용할 DTO
// 채팅방 아이디, 채팅방 이름, 프로젝트 이름, 메세지 리스트, 사원 리스트,

@Data
@NoArgsConstructor
public class ChatWithEmployeesDTO {
	private Long id;
    private String name;
    private List<EmployeeDTO> employees;
}
