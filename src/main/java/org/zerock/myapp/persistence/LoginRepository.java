package org.zerock.myapp.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.zerock.myapp.entity.Employee;




public interface LoginRepository extends JpaRepository<Employee, String> {

	public abstract Otional<Employee> findByLoginIdAnd
}
