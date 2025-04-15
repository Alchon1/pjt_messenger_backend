package org.zerock.myapp.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.zerock.myapp.domain.ProjectDTO;
import org.zerock.myapp.entity.Project;

public interface ProjectService {
	
	public abstract List<Project> getAllList();     			// 전체 조회
	
	public abstract Page<Project> getSearchList(ProjectDTO dto, Pageable paging); 	// 전체 조회(검색)
	public abstract Page<Project> getUpCommingList(Pageable paging);
	
	public abstract Project create(ProjectDTO dto);    			// 생성 처리
	public abstract Project getById(Long id);    				// 단일 조회
	public abstract Project update(Long id, ProjectDTO dto);    // 수정 처리
	public abstract String deleteById(Long id);					// 삭제 처리
	
}//end interface