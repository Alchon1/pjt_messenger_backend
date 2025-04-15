package org.zerock.myapp.persistence;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import org.zerock.myapp.entity.Employee;


/**
 * 회원 Repository
 */

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, String>, JpaSpecificationExecutor<Employee> {

<<<<<<< Updated upstream
;;
	
=======
	public abstract Boolean existsByLoginId(String loginId);
	public abstract long countByEmpnoStartingWith(String empnoPrefix);
>>>>>>> Stashed changes
} // end interface
