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

    private List<Employee> empList;
    private List<Project> pjList; 
    private List<Department> dtList;


    
}//ChatInitResponseDTO