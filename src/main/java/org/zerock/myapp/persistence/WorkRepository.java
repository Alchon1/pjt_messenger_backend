package org.zerock.myapp.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import org.zerock.myapp.entity.Employee;
import org.zerock.myapp.entity.Work;


/**
 * 업무 Repository
 */

@Repository
public interface WorkRepository extends JpaRepository<Work, Long>, JpaSpecificationExecutor<Work> {
	
	List<Work> findByEnabledAndEmployee(Boolean enabled, Employee employee);
	
} // end interface
