package org.zerock.myapp.service;

import java.util.List;

import org.zerock.myapp.domain.DepartmentByOrgaDTO;
import org.zerock.myapp.entity.Department;

public interface DepartmentService {
	
	public abstract List<Department> getAllList();     			// 전체 조회
	
	public abstract Department getById(String id);    // 단일 조회
	
	DepartmentByOrgaDTO buildTree(Long rootId);
	
}//end interface