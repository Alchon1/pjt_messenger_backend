package org.zerock.myapp.service;

import java.text.ParseException;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.zerock.myapp.domain.ProjectDTO;
import org.zerock.myapp.entity.Project;
import org.zerock.myapp.exception.ServiceException;

public interface ProjectService {
	
	public abstract Page<Project> getSearchList(ProjectDTO dto, Pageable paging); 		// 전체 조회(검색)
	public abstract Page<Project> getSearchListData(ProjectDTO dto, Pageable paging);	// 
	
	public abstract Page<Project> getUpComingList(Pageable paging);
	public abstract List<Project> getStatusAllList();
	
	public abstract Project create(ProjectDTO dto) throws ServiceException, ParseException;    			// 생성 처리
	public abstract Project getById(Long id);    														// 단일 조회
	public abstract Project update(Long id, ProjectDTO dto) throws ServiceException, ParseException;    // 수정 처리
	public abstract Project deleteById(Long id) throws ServiceException;									// 삭제 처리
	
}//end interface