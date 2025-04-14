package org.zerock.myapp.domain;

import java.util.List;

import org.zerock.myapp.entity.Department;
import org.zerock.myapp.entity.Employee;
import org.zerock.myapp.entity.Project;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class ChatInitResponseDTO {
//    private List<Employee> employees  = new Vector<>();;
//    private List<Project> projects = new Vector<>();;
//    private List<Department> department = new Vector<>();;
    private List<Employee> empList;
    private List<Project> pjList; 
    private List<Department> dtList;

//    public ChatInitResponseDTO(List<Employee> empList, List<Project> pjList, List<Departments>dtList) {
//    this.empList=empList;
//    this.pjList=pjList;
//    this.dtList=dtList;
//    }//@AllArgsConstructor
    
}//ChatInitResponseDTO