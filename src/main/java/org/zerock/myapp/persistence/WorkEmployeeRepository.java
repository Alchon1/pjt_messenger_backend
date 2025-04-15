package org.zerock.myapp.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import org.zerock.myapp.entity.Employee;
import org.zerock.myapp.entity.WorkEmployee;
import org.zerock.myapp.entity.WorkEmployeePK;


/**
 * 업무 Repository
 */

@Repository
public interface WorkEmployeeRepository extends JpaRepository<WorkEmployee, WorkEmployeePK>, JpaSpecificationExecutor<WorkEmployee> {

	// 담당자 조회
	List<WorkEmployee> findByEnabledAndId_Empno(Boolean enabled, String empno);
	
	// workId와 enabled로 조회
	List<WorkEmployee> findByEnabledAndId_WorkId(Boolean enabled, Long workId);

} // end interface
