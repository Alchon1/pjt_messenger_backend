package org.zerock.myapp.persistence;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import org.zerock.myapp.entity.Department;
import org.zerock.myapp.entity.Employee;

/**
 * 회원 Repository
 */

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, String>, JpaSpecificationExecutor<Employee> {

	// 회원가입시 똑같은 아이디가 db에 저장되어 있는지 검증.
	public abstract Boolean existsByLoginId(String loginId);

	

	public abstract long countByEmpnoStartingWith(String empnoPrefix);

	Optional<Employee> findByEnabledAndEmpno(Boolean enabled, String empno);

	List<Employee> findByEnabledAndDepartment(Boolean b, Department entity);

	List<Employee> findByEnabledAndPositionInOrderByPositionAscCrtDateDesc(Boolean enabled, Integer[] positions);

	Page<Employee> findByNameContainingAndEnabledTrue(String name, Pageable pageable);
	Page<Employee> findByTelContainingAndEnabledTrue(String tel, Pageable pageable);
} // end interface
