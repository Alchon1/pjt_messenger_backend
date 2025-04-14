package org.zerock.myapp.persistence;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import org.zerock.myapp.entity.Project;


/**
 * 프로젝트 Repository
 * -- , JpaSpecificationExecutor<Project> --extends 제외, 간단한 pjt에서는 사용할 필요 없음
 */

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long>, JpaSpecificationExecutor<Project> {

	/* 검색
	 * ** 기본 조건 => 활성화상태(enabled)
	 * 2. 검색어 
	 * 		- 프로젝트명
	 * */
	
	//검색 리스트: 활성화상태(true) 
	public abstract Page<Project> findByEnabled(Boolean enabled, Pageable paging);
	
	//검색 리스트: 활성화상태(true) + status
	public abstract Page<Project> findByEnabledAndStatus(Boolean enabled, Integer status, Pageable paging);
	
	//검색 리스트: 활성화상태(true) + status + 프로젝트명 
	public abstract Page<Project> findByEnabledAndStatusAndNameContaining(Boolean enabled, Integer status, String name, Pageable paging);
	
	//검색 리스트: 활성화상태(true) + status + detail 
	public abstract Page<Project> findByEnabledAndStatusAndDetailContaining(Boolean enabled, Integer status, String detail, Pageable paging);

	//검색 리스트: 활성화상태(true) + 프로젝트명 
	public abstract Page<Project> findByEnabledAndNameContaining(Boolean enabled, String name, Pageable paging);

	//검색 리스트: 활성화상태(true) + detail 
	public abstract Page<Project> findByEnabledAndDetailContaining(Boolean enabled, String detail, Pageable paging);
	
	public abstract Optional<Project> findByEnabledAndId(Boolean enabled, Long id);
	
	
	
} // end interface
