package org.zerock.myapp.persistence;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.zerock.myapp.domain.EmployeeHierarchyDTO;
import org.zerock.myapp.entity.Department;
import org.zerock.myapp.entity.Employee;

/**

- 회원 Repository
*/

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, String>, JpaSpecificationExecutor<Employee> {


		// 회원가입시 똑같은 아이디가 db에 저장되어 있는지 검증.
		public abstract Boolean existsByLoginId(String loginId);
		
		public abstract long countByEmpnoStartingWith(String empnoPrefix);
		
		Optional<Employee> findByEnabledAndEmpno(Boolean enabled, String empno);
		
		List<Employee> findByEnabledAndDepartment(Boolean b, Department entity);
		List<Employee> findByNameContainingAndEnabledTrue(String name);
		List<Employee> findByTelContainingAndEnabledTrue(String tel);
		List<Employee> findByEnabledAndDepartment_Id(Boolean enabled, Long deptId);

	@Query(
	        value = """
	            SELECT
	                e.empno AS empno,
	                dd.name AS department,
	                c.name AS position,
	                e.name AS name,
	                dd.path AS path
	            FROM t_employee e
	            LEFT JOIN (
	                WITH org_tree (id, name, p_dept_id, depth, org_name, path) AS (
	                    SELECT id, name, p_dept_id, 1 AS depth, name AS org_name, TO_CHAR(id) AS path
	                    FROM t_department
	                    WHERE p_dept_id IS NULL
	                    UNION ALL
	                    SELECT d.id, d.name, d.p_dept_id, t.depth + 1,
	                           LPAD(' ', (t.depth + 1) * 4) || d.name,
	                           t.path || ' > ' || TO_CHAR(d.id)
	                    FROM t_department d
	                    JOIN org_tree t ON d.p_dept_id = t.id
	                )
	                SELECT * FROM org_tree
	            ) dd ON e.dept_id = dd.id
	            LEFT JOIN t_code c ON c.category = 'employee_position' AND e.position = c.code
	            WHERE e.enabled = :enabled AND e.position IN (:positions)
	            ORDER BY dd.path, e.position DESC
	        """,
	        nativeQuery = true
	    )
	    List<EmployeeHierarchyDTO> findHierarchy(@Param("enabled") Boolean enabled, @Param("positions") Integer[] positions);



} // end interface