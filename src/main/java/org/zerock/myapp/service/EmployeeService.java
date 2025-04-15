package org.zerock.myapp.service;

import java.util.Date;
import java.util.List;

import org.zerock.myapp.domain.EmployeeDTO;
import org.zerock.myapp.entity.Employee;

public interface EmployeeService {
	
	public abstract List<Employee> getAllList();     			// 전체 조회
	public abstract List<Employee> getSearchList(EmployeeDTO dto); // 전체 조회(검색)
	
	public abstract Boolean create(EmployeeDTO dto);    	// 생성 처리
	public abstract Employee getById(String id);    // 단일 조회
	public abstract Boolean update(EmployeeDTO dto);    	// 수정 처리
	public abstract Boolean deleteById(String id);// 삭제 처리
	public String checkIdDuplicate(String loginId);
	    String getRolePrefixFromPosition(Integer position);

    public abstract String generateEmpno(String rolePrefix, Date date);



	
}//end interface