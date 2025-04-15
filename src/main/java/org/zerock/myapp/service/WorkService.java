package org.zerock.myapp.service;

import java.util.List;

import org.zerock.myapp.domain.WorkDTO;
import org.zerock.myapp.entity.Employee;
import org.zerock.myapp.entity.Work;


public interface WorkService {
	
	// 담당자 조회
//	public abstract List<WorkEmployee> findByEnabledAndId_Empno(Boolean enabled, String empno);
	// 1. 담당자 조회: WorkEmployee 목록 반환
//	public abstract List<WorkEmployee> getWorkEmployeesByEnabledAndEmpno(Boolean enabled, String empno);
	
	// 담당자 업무 조회 (WorkEmployee → Work 변환)
    public abstract List<Work> getManagedWorksByEnabledAndEmpno(Boolean enabled, String empno);

	// 지시자 업무 조회
//	public abstract List<Work> findByEnabledAndEmployee(Boolean enabled, Employee employee);
	// 2. 지시자 조회: Employee가 할당된 Work 목록 반환
	public abstract List<Work> getWorksByEnabledAndEmployee(Boolean enabled, Employee employee);

	// 3. 생성: Work와 연관된 WorkEmployee 함께 처리
    public abstract Boolean createWorkWithEmployees(WorkDTO dto, List<String> empnos);
	
	
	public abstract WorkDTO getById(Long id); 		 // 단일 조회
	public abstract Boolean update(WorkDTO dto, List<String> empnos); // 수정 처리
	public abstract Boolean deleteById(Long id); // 삭제 처리

}//end interface