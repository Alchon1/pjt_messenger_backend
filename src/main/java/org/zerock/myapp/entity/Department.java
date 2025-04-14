package org.zerock.myapp.entity;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import org.hibernate.annotations.CurrentTimestamp;
import org.hibernate.annotations.SourceType;
import org.hibernate.generator.EventType;
import org.zerock.myapp.util.BooleanToIntegerConverter;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.ToString;


@Data

//JSON 으로 변환해서 보낼때, 제외 할 항목
@JsonIgnoreProperties({
	"udtDate"
})

// 부서 entity

@Entity
@Table(name="T_DEPARTMENT")
public class Department implements Serializable {
	@Serial private static final long serialVersionUID = 1L;

	//1. pk
	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID", unique=true, nullable=false)
	private Long id; // 부서 id

	@Column(nullable=false)
	private String name; // 부서명

	@Column(nullable=false)
	private Integer depth; // 계층 깊이

	@Convert(converter = BooleanToIntegerConverter.class)
	@Column(nullable=false)
	private Boolean enabled = true; // 활성화상태(1=유효,0=삭제)

	
	@CurrentTimestamp(event = EventType.INSERT, source = SourceType.DB)
	@Column(name="CRT_DATE", nullable = false)
	private Date crtDate;

	@CurrentTimestamp(event = EventType.UPDATE, source = SourceType.DB)
	@Column(name="UDT_DATE")
	private Date udtDate;

	
	// join
//	@ToString.Exclude
//	@ManyToOne
//	@JoinColumn(name="P_DEPT_ID")
//	private Department Department; // 상위부서

//	@ToString.Exclude
//	@OneToMany(mappedBy="Department")
//	@JsonIgnore
//	private List<Department> Departments = new Vector<>(); // 부서
//
//	
//	@ToString.Exclude
//	@OneToMany(mappedBy="Department")
//	@JsonIgnore
//	private List<Employee> Employees = new Vector<>(); // 사원


//	public Department addDepartment(Department Department) {
//		getDepartments().add(Department);
//		Department.setDepartment(this);
//
//		return Department;
//	} // addDepartment
//
//	public Department removeDepartment(Department Department) {
//		getDepartments().remove(Department);
//		Department.setDepartment(null);
//
//		return Department;
//	} // removeDepartment
//
//	public Employee addEmployee(Employee Employee) {
//		getEmployees().add(Employee);
//		Employee.setDepartment(this);
//
//		return Employee;
//	} // addEmployee
//
//	public Employee removeEmployee(Employee Employee) {
//		getEmployees().remove(Employee);
//		Employee.setDepartment(null);
//
//		return Employee;
//	} // removeEmployee

} // end class