package org.zerock.myapp.service;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.zerock.myapp.domain.WorkDTO;
import org.zerock.myapp.entity.Employee;
import org.zerock.myapp.entity.Work;
import org.zerock.myapp.entity.WorkEmployee;
import org.zerock.myapp.entity.WorkEmployeePK;
import org.zerock.myapp.persistence.EmployeeRepository;
import org.zerock.myapp.persistence.WorkEmployeeRepository;
import org.zerock.myapp.persistence.WorkRepository;

import jakarta.annotation.PostConstruct;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@NoArgsConstructor

@Service
public class WorkServiceImpl implements WorkService {
    @Autowired WorkRepository dao;
    @Autowired EmployeeRepository EDao;
    @Autowired WorkEmployeeRepository WEDao;
	
	
	@PostConstruct
    void postConstruct(){
        log.debug("WorkServiceImpl -- postConstruct() invoked");
        log.debug("dao: {}", this.dao);
    }//postConstruct


	// 담당자 조회
//	@Override
//	public List<WorkEmployee> getWorkEmployeesByEnabledAndEmpno(Boolean enabled, String empno) {	
//		log.debug("WorkServiceImpl -- findByEnabledAndEmpno({},{}) invoked",enabled,empno);
//		
//		List<WorkEmployee> list = this.WEDao.findByEnabledAndId_Empno(true, empno);
//		
//		return list;
//	} // findByEnabledAndEmpno
	
	// 담당자의 업무 조회
	@Override
	public List<Work> getManagedWorksByEnabledAndEmpno(
			Boolean enabled, 
			String empno
			) {
		log.debug("WorkServiceImpl -- findByEnabledAndEmpno({},{}) invoked",enabled,empno);
		
	    List<WorkEmployee> workEmployees = this.WEDao.findByEnabledAndId_Empno(enabled, empno);
	    
	 // WorkEmployee → Work 변환
	    List<Work> list = workEmployees.stream()
	            .map(we -> we.getWork()) // WorkEmployee에서 Work 추출
	            .collect(Collectors.toList());
	    
	    return list;
	} // getManagedWorksByEnabledAndEmpno
	
	// 요청자의 업무 조회
	@Override
	public List<Work> getWorksByEnabledAndEmployee(
			Boolean enabled, 
			Employee employee
			) {
		log.debug("WorkServiceImpl -- findByEnabledAndEmployee({},{}) invoked",enabled,employee);
		
		List<Work> list = this.dao.findByEnabledAndEmployee(true, employee);
		
		return list;
	} // findByEnabledAndEmployee
	
	
	@Override
	public Boolean createWorkWithEmployees(
			WorkDTO dto, 
			List<String> empnos
			) {	//등록 처리
		log.debug("WorkServiceImpl -- create({}) invoked", dto, empnos);
		
		try {
			// DTO -> Entity 변환
	        Work work = Work.toEntity(dto);
	        
	        // DB에 저장
	        Work savedWork = this.dao.save(work);
	        
	        // 3. WorkEmployee 저장 로직
	        empnos.forEach(empno -> {
	        	// 1. Employee 조회 (empno 기준)
	        	Employee employee = this.EDao.findByEnabledAndEmpno(true, empno)
	        			.orElseThrow(() -> new RuntimeException("Employee not found with empno: " + empno));
	            
	        	// 2. pk값 생성
	        	WorkEmployeePK pk = new WorkEmployeePK();
	            pk.setWorkId(savedWork.getId());
	            pk.setEmpno(empno);
	            	
	            // 3. 저장한 pk값으로 work_employee 생성
	            WorkEmployee we = new WorkEmployee();
	            we.setId(pk);
	            we.setEnabled(true);
	            we.setWork(savedWork);
	            we.setEmployee(employee);

	            this.WEDao.save(we);
	        }); // forEach
	        
	        // 성공
	        return true;
		} catch(Throwable e){
			log.info("생성 실패.dto({}), e({})",dto,e.getMessage());
			return false;
		} finally {
			log.info("create logic end");
		} // try-catch-finally
        
	} // create
	
	@Override
	public WorkDTO getById(Long id) {	// 단일 조회
		log.debug("WorkServiceImpl -- getById({}) invoked", id);
		
		// 값이 존재하면 반환하고, 없으면 new Course()와 같은 기본값을 반환합니다.
		Work work = this.dao.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Invalid Work ID: " + id));
		
		// 엔티티 -> DTO
		WorkDTO dto = work.toDTO();

	    // workId로 WorkEmployee 리스트 조회
	    List<WorkEmployee> workEmployees = this.WEDao.findByEnabledAndId_WorkId(true, id);
	    dto.setWorkEmployees(workEmployees);
		
		return dto;
	} // getById
	
	@Override
	public Boolean update(
			WorkDTO dto,
			List<String> empnos
			) { //수정 처리
		log.debug("WorkServiceImpl -- update({}) invoked", dto, empnos);
		
		try {
	        // 1. 기존 데이터 조회
	        Work existingWork = this.dao.findById(dto.getId())
	                .orElseThrow(() -> new RuntimeException("Work not found with id: " + dto.getId()));
	        
	        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	        // 2. 필요한 필드만 업데이트
	        existingWork.setName(dto.getName());
	        existingWork.setDetail(dto.getDetail());
	        existingWork.setMemo(dto.getMemo());
	        existingWork.setStatus(dto.getStatus());
	        existingWork.setType(dto.getType());
	        existingWork.setStartDate(sdf.parse(dto.getStartDate()));
	        existingWork.setEndDate(sdf.parse(dto.getEndDate()));
	        
	        // 3. 저장
	        this.dao.save(existingWork);
	        log.debug("Updated Work: {}", existingWork);
	        
	        
	        // 4. 담당자 처리
	        Long workId = existingWork.getId();

	        if (empnos == null) {
	            // 4-1. empnos가 null인 경우: 모든 담당자 비활성화
	            List<WorkEmployee> existingAssignees = this.WEDao.findByEnabledAndId_WorkId(true, workId);
	            existingAssignees.forEach(we -> {
	                we.setEnabled(false);
	                WEDao.save(we);
	            }); // forEach
	            log.debug("All WorkEmployees disabled for Work ID: {}", workId);
	        } else {
	            // 4-2. empnos가 제공된 경우: 기존 담당자 비활성화 후 새로운 담당자 추가
	            List<WorkEmployee> existingAssignees = this.WEDao.findByEnabledAndId_WorkId(true, workId);
	            existingAssignees.forEach(we -> {
	                we.setEnabled(false);
	                WEDao.save(we);
	            }); // forEach

	            empnos.forEach(empno -> {
	                WorkEmployeePK pk = new WorkEmployeePK();
	                pk.setWorkId(workId);
	                pk.setEmpno(empno);

	                WorkEmployee we = WEDao.findById(pk).orElse(null);

	                if (we != null) {
	                    // 기존 WorkEmployee 활성화
	                    we.setEnabled(true);
	                } else {
	                    // 새로운 WorkEmployee 생성
	                    Employee employee = this.EDao.findByEnabledAndEmpno(true, empno)
	                            .orElseThrow(() -> new RuntimeException("Employee not found or inactive: " + empno));

	                    we = new WorkEmployee();
	                    we.setId(pk);
	                    we.setEnabled(true);
	                    we.setWork(existingWork);
	                    we.setEmployee(employee);
	                } // if-else

	                this.WEDao.save(we);
	            }); // forEach
	            log.debug("Updated WorkEmployees for Work ID: {}", workId);
	        } // if-else
	        
	        return true;
		} catch (Exception e) {
	        log.error("Update failed: {}", e.getMessage(), e);
	        return false;
	    } // try-catch

	} // update

	@Override
	public Boolean deleteById(Long id) { // 삭제 처리
		log.debug("WorkServiceImpl -- deleteById({}) invoked", id);
		
		try {
	        // 1. 기존 데이터 조회
	        Work existingWork = this.dao.findById(id)
	                .orElseThrow(() -> new RuntimeException("Work not found with id: " + id));

	        // 2. Work 비활성화
	        existingWork.setEnabled(false);
	        this.dao.save(existingWork);
	        
	        // 3. 연관된 WorkEmployee 비활성화
	        List<WorkEmployee> relatedWorkEmployees = 
	        		this.WEDao.findByEnabledAndId_WorkId(true, id);
	        
	        relatedWorkEmployees.forEach(we -> {
	            we.setEnabled(false); // 연관된 WorkEmployee도 비활성화
	            this.WEDao.save(we); // 저장
	        }); // forEach

	        // 4. 저장 (업데이트)
	        this.dao.save(existingWork);
	        log.debug("Disabled Work: {}", existingWork);

	        return true;
	    } catch (Exception e) {
	        log.error("Delete failed: {}", e.getMessage(), e);
	        return false;
	    } // try-catch
		
	} // deleteById
	
}//end class
