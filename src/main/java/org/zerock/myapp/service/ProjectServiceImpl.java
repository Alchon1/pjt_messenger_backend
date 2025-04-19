package org.zerock.myapp.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.zerock.myapp.domain.ProjectDTO;
import org.zerock.myapp.entity.Project;
import org.zerock.myapp.exception.ServiceException;
import org.zerock.myapp.persistence.EmployeeRepository;
import org.zerock.myapp.persistence.ProjectRepository;
import org.zerock.myapp.util.DateTimeUtils;

import jakarta.annotation.PostConstruct;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@NoArgsConstructor

@Service
public class ProjectServiceImpl implements ProjectService {
	@Autowired
	ProjectRepository dao;
	@Autowired
	EmployeeRepository empDao;
	@Autowired 
	JwtProvider jwt;

	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

	@PostConstruct
	void postConstruct() {
		log.debug("\t+ ProjectServiceImpl -- postConstruct() invoked");
		log.debug("dao: {}", this.dao);
	}// postConstruct

	@Override
	public Page<Project> getSearchList(ProjectDTO dto, Pageable paging) {
		log.debug("\t+ ProjectServiceImpl -- getSearchList(()) invoked", dto);

		Page<Project> pageList = getSearchListData(dto, paging);
		pageList.forEach(data -> {
			data.setEndDday(DateTimeUtils.getDday(data.getEndDate()));
		});

		return pageList;
	} // getSearchList

	@Override
	public Page<Project> getSearchListData(ProjectDTO dto, Pageable paging) {
		log.debug("\t+ ProjectServiceImpl -- getSearchListData({}) invoked", dto);
		
		if(dto.getSearchWord() != null && dto.getSearchWord().length() == 0) dto.setSearchWord(null);
		if(dto.getSearchText() != null && dto.getSearchText().length() == 0) dto.setSearchText(null);

		if (dto.getStatus() == null && dto.getSearchText() == null) {
			// 검색 리스트: 활성화상태(true)
			return this.dao.findByEnabled(true, paging);

		} else if (dto.getStatus() != null && dto.getSearchText() == null) {
			// 검색 리스트: 활성화상태(true) + status
			return this.dao.findByEnabledAndStatus(true, dto.getStatus(), paging);

		} else if (dto.getStatus() == null && dto.getSearchText() != null) {
			return switch (dto.getSearchWord()) {
			case "name" -> this.dao.findByEnabledAndNameContaining(true, dto.getSearchText(), paging);
			case "detail" -> this.dao.findByEnabledAndDetailContaining(true, dto.getSearchText(), paging);
			default -> throw new IllegalArgumentException("swich_1 - Invalid search word: " + dto.getSearchWord());
			};

		} else if (dto.getStatus() != null && dto.getSearchText() != null) {
			return switch (dto.getSearchWord()) {
			case "name" ->
				this.dao.findByEnabledAndStatusAndNameContaining(true, dto.getStatus(), dto.getSearchText(), paging);
			case "detail" ->
				this.dao.findByEnabledAndStatusAndDetailContaining(true, dto.getStatus(), dto.getSearchText(), paging);
			default -> throw new IllegalArgumentException("swich_2 - Invalid search word: " + dto.getSearchWord());
			};
		}

		return null;
	} // getSearchListData

	@Override
	public Page<Project> getUpComingList(Pageable paging) {
		log.debug("\t+ ProjectServiceImpl -- getUpComingList() invoked");

		Integer[] status = { 1, 2 };
		Page<Project> pageList = this.dao.findByEnabledAndStatusIn(true, status, paging);
		pageList.forEach(data -> {
			data.setEndDday(DateTimeUtils.getDday(data.getEndDate()));
		});
		return pageList;
	} // getUpComingList

	@Override
	public List<Project> getStatusAllList() {
		log.debug("\t+ ProjectServiceImpl -- getStatusAllList() invoked");

		Integer[] status = { 1, 2 };
		List<Project> pageList = this.dao.findByEnabledAndStatusInOrderByCrtDateDesc(true, status);
		pageList.forEach(data -> {
			data.setEndDday(DateTimeUtils.getDday(data.getEndDate()));
		});
		return pageList;
	} // getStatusAllList

	@Override
	public Project create(ProjectDTO dto) throws ServiceException, ParseException { // 등록 처리
		log.debug("\t+ ProjectServiceImpl -- create({}) invoked", dto);

		try {
			dto.setCreatorEmpno("E2110002");
						
			
			Project project = new Project();
			
			project.setName(dto.getName());
			project.setStartDate(this.sdf.parse(dto.getStartDate()));
			project.setEndDate(this.sdf.parse(dto.getEndDate()));
			project.setStatus(dto.getStatus());
			project.setDetail(dto.getDetail());
			project.setPjtManager(this.empDao.findById(dto.getManagerEmpno()).orElse(null));
			project.setPjtCreator(this.empDao.findById(dto.getCreatorEmpno()).orElse(null));

			Project result = this.dao.save(project);

			return result;
		} catch (ParseException e) {
			// 날짜 파싱 실패 시
			throw e; // 메서드 시그니처에 throws ParseException이 있으므로 다시 던짐

		} catch (Exception e) {
			throw new ServiceException("프로젝트 생성 중 오류가 발생했습니다.", e);
		}
	} // create

	@Override
	public Project getById(Long id) { // 단일 조회
		log.debug("\t+ ProjectServiceImpl -- getById({}) invoked", id);

		Project project = this.dao.findByEnabledAndId(true, id)
				.orElseThrow(() -> new RuntimeException("해당 건이 조회되지 않습니다. - " + id));

		return project;
	} // getById

	@Override
	public Project update(Long id, ProjectDTO dto) throws ServiceException, ParseException {// 수정 처리
		log.debug("\t+ ProjectServiceImpl -- update({}) invoked", dto);
	
		try {
			Project project = this.dao.findByEnabledAndId(true, id).orElseThrow(() -> new RuntimeException("해당 건이 조회되지 않습니다. - " + id));
	
			if (dto.getName() != null)
				project.setName(dto.getName());
			if (dto.getStartDate() != null)
				project.setStartDate(this.sdf.parse(dto.getStartDate()));
			if (dto.getEndDate() != null)
				project.setEndDate(this.sdf.parse(dto.getEndDate()));
			if (dto.getStatus() != null)
				project.setStatus(dto.getStatus());
			if (dto.getDetail() != null)
				project.setDetail(dto.getDetail());
			if (dto.getManagerEmpno() != null)
				project.setPjtManager(this.empDao.findById(dto.getManagerEmpno()).orElse(null));
	
			Project result = this.dao.save(project);
			log.info("Update success: {}", result);
	
			return result;
		} catch (ParseException e) {
			// 날짜 파싱 실패 시
			throw e; // 메서드 시그니처에 throws ParseException이 있으므로 다시 던짐
	
		} catch (Exception e) {
			throw new ServiceException("프로젝트 수정 중 오류가 발생했습니다.", e);
		}
	} // update

	@Override
	public Project deleteById(Long id) throws ServiceException { // 삭제 처리
		log.debug("\t+ ProjectServiceImpl -- deleteById({}) invoked", id);

		try {
			Optional<Project> optionalProject = this.dao.findByEnabledAndId(true, id);
	
			if (optionalProject.isPresent()) {
				Project project = optionalProject.get();
				project.setEnabled(false);
	
				Project result = this.dao.save(project);
				log.info("Delete success");
	
				return result;
			} // if
		}  catch (Exception e) {
			throw new ServiceException("프로젝트 삭제 중 오류가 발생했습니다.", e);
		}
		return null;
	}// deleteById

}// end class
