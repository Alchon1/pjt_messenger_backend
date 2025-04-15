package org.zerock.myapp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.zerock.myapp.domain.DepartmentByOrgaDTO;
import org.zerock.myapp.entity.Department;
import org.zerock.myapp.persistence.DepartmentRepository;

import jakarta.annotation.PostConstruct;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@NoArgsConstructor

@Service
public class DepartmentServiceImpl implements DepartmentService {
    @Autowired DepartmentRepository dao;
	
    
    @Override
    public DepartmentByOrgaDTO buildTree(Long id) {
        Department root = dao.findById(id).orElseThrow();
        return convertToDTO(root);
    }
    private DepartmentByOrgaDTO convertToDTO(Department dept) {
        DepartmentByOrgaDTO dto = new DepartmentByOrgaDTO(dept.getName());
        dto.setId(dept.getId());

        // 자식 부서가 있다면 재귀로 처리
        if (dept.getDepartments() != null && !dept.getDepartments().isEmpty()) {
            for (Department child : dept.getDepartments()) {
                dto.getOrga().add(convertToDTO(child));
            }
        }

        return dto;
    }
    
	
	@PostConstruct
    void postConstruct(){
        log.debug("DepartmentServiceImpl -- postConstruct() invoked");
        log.debug("dao: {}", dao);
    }//postConstruct
	


	@Override
	public List<Department> getAllList() {	//검색 없는 전체 리스트
		log.debug("DepartmentServiceImpl -- getAllList() invoked");
		
		List<Department> list = dao.findAll();
		
		return list;
	} // getAllList
	
	@Override
	public Department getById(String id) {	// 단일 조회
		log.debug("DepartmentServiceImpl -- getById({}) invoked", id);
		
		//값이 존재하면 반환하고, 없으면 new Course()와 같은 기본값을 반환합니다.
		Department data = new Department();//dao.findById(id).orElse(new Department());
		
		return data;
	} // getById
	
}//end class
