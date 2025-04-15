package org.zerock.myapp.domain;

import java.util.List;
import java.util.Vector;

import lombok.Data;

@Data
public class DepartmentByOrgaDTO {
	private String name;
    private Long id;
	private List<DepartmentByOrgaDTO> orga= new Vector<>();



	public DepartmentByOrgaDTO(String name) {
		this.name=name;
	}
	
	
}
