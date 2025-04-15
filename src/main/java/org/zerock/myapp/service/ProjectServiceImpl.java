package org.zerock.myapp.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.zerock.myapp.domain.ProjectDTO;
import org.zerock.myapp.entity.Project;
import org.zerock.myapp.persistence.EmployeeRepository;
import org.zerock.myapp.persistence.ProjectRepository;

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

	@PostConstruct
	void postConstruct() {
		log.debug("\t+ ProjectServiceImpl -- postConstruct() invoked");
		log.debug("dao: {}", this.dao);
	}// postConstruct

	@Override
	public List<Project> getAllList() {	//검색 없는 전체 리스트
		log.debug("ProjectServiceImpl -- getAllList() invoked");
		
		List<Project> list = dao.findAll();
		
		return list;
	} // getAllList
	
	@Override
	public Page<Project> getSearchList(ProjectDTO dto, Pageable paging) {
		log.debug("\t+ ProjectServiceImpl -- getSearchList(()) invoked", dto);

		if (dto.getStatus() == null && dto.getSearchText() == null) {
			// 검색 리스트: 활성화상태(true)
			log.debug("\t+ 검색 리스트: 활성화상태(true) ");
			return this.dao.findByEnabled(true, paging);
		} else if (dto.getStatus() != null && dto.getSearchText() == null) {
			// 검색 리스트: 활성화상태(true) + status
			log.debug("\t+ 검색 리스트: 활성화상태(true) + status ");
			return this.dao.findByEnabledAndStatus(true, dto.getStatus(), paging);
		} else if (dto.getStatus() == null && dto.getSearchText() != null) {
			log.debug("\t+ 검색 리스트: 3 ");
			return switch (dto.getSearchWord()) {
			case "name" -> this.dao.findByEnabledAndNameContaining(true, dto.getSearchText(), paging);
			case "detail" -> this.dao.findByEnabledAndDetailContaining(true, dto.getSearchText(), paging);
			default -> throw new IllegalArgumentException("swich_1 - Invalid search word: " + dto.getSearchWord());
			};
		} else if (dto.getStatus() != null && dto.getSearchText() != null) {
			log.debug("\t+ 검색 리스트: 4 " + dto.getSearchWord() + " / " + dto.getSearchText());
			return switch (dto.getSearchWord()) {
			case "name" -> {
				log.info("test:{}", dto.getSearchWord());
				yield this.dao.findByEnabledAndStatusAndNameContaining(true, dto.getStatus(), dto.getSearchText(),
						paging);
			}
			case "detail" ->
				this.dao.findByEnabledAndStatusAndDetailContaining(true, dto.getStatus(), dto.getSearchText(), paging);
			default -> throw new IllegalArgumentException("swich_2 - Invalid search word: " + dto.getSearchWord());
			};
		}

		return null;
	} // getSearchList

	@Override
	public Page<Project> getUpCommingList(Pageable paging) {
		log.debug("\t+ ProjectServiceImpl -- getUpCommingList(()) invoked");

		return this.dao.findByEnabled(true, paging);
	} // getUpCommingList

	@Override
	public Project create(ProjectDTO dto) { // 등록 처리
		log.debug("\t+ ProjectServiceImpl -- create({}) invoked", dto);

		Project project = new Project();

		project.setName(dto.getName());
		project.setStartDate(LocalDate.parse(dto.getStartDate()));
		project.setEndDate(LocalDate.parse(dto.getEndDate()));
		project.setStatus(dto.getStatus());
		project.setDetail(dto.getDetail());
		project.setPjtManager(this.empDao.findById(dto.getManagerEmpno()).orElse(null));
		project.setPjtCreator(this.empDao.findById(dto.getCreatorEmpno()).orElse(null));

		log.info("before success?");
		log.info("\t\tproject1111:{}", project);
		
		Project result = this.dao.save(project);
		log.info("result:{}", result);
		log.info("Regist success");

		return result;
	} // create

	@Override
	public Project getById(Long id) { // 단일 조회
		log.debug("\t+ ProjectServiceImpl -- getById({}) invoked", id);

		Project project = this.dao.findByEnabledAndId(true, id).orElseThrow(() -> new RuntimeException("해당 건이 조회되지 않습니다. - " + id));

		return project;
	} // getById

	@Override
	public Project update(Long id, ProjectDTO dto) {// 수정 처리
		log.debug("\t+ ProjectServiceImpl -- update({}) invoked", dto);

		Project project = this.dao.findByEnabledAndId(true, id).orElseThrow(() -> new RuntimeException("해당 건이 조회되지 않습니다. - " + id));

		project.setName(dto.getName());
		project.setStartDate(LocalDate.parse(dto.getStartDate()));
		project.setEndDate(LocalDate.parse(dto.getEndDate()));
		project.setStatus(dto.getStatus());
		project.setDetail(dto.getDetail());
		project.setPjtManager(this.empDao.findById(dto.getManagerEmpno()).orElse(null));
		
		Project result = this.dao.save(project);
	    log.info("Update success: {}", result);

		return result;
	} // update

	@Override
	public String deleteById(Long id) { // 삭제 처리
		log.debug("\t+ ProjectServiceImpl -- deleteById({}) invoked", id);

		Optional<Project> optionalProject = this.dao.findByEnabledAndId(true, id);

		if (optionalProject.isPresent()) {
			Project project = optionalProject.get();
			project.setEnabled(false);

			this.dao.save(project);

			return "프로젝트가 삭제되었습니다.";
		} // if

		return "프로젝트 삭제가 실패하였습니다.";

	}// deleteById

}// end class
