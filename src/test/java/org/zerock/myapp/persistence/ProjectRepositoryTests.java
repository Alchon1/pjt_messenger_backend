package org.zerock.myapp.persistence;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.sql.SQLException;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.Timeout;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.annotation.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import org.zerock.myapp.entity.Project;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@NoArgsConstructor

@TestInstance(Lifecycle.PER_CLASS)
@TestMethodOrder(OrderAnnotation.class)

@Transactional

@SpringBootTest
public class ProjectRepositoryTests {
	@Autowired private ProjectRepository repo;
	
	@BeforeAll
	void beforeAll() throws SQLException {
		log.debug("ProjectRepositoryTests -- beforeAll() invoked.");
		
		assertNotNull(this.repo);
		log.info("\t + this.repo: {}", this.repo);		
	}//beforeAll
	
	
	
	@Disabled
	@Tag("Project]-Repository-Test")
	@Order(1)
	@Test
//	@RepeatedTest(1)
	@DisplayName("1. create")
	@Timeout(value = 1L, unit = TimeUnit.SECONDS)
	@Rollback(false)
	void create() {
		log.debug("create() invoked");
		
		Project pjt = new Project();
		
		log.info("\t+ Before: {}", pjt);				
		pjt = this.repo.save(pjt);			
		log.info("\t+ After: {}", pjt);		
				
	}//create
	
//	@Disabled
	@Tag("Project-Repository-Test")
	@Order(2)
	@Test
//	@RepeatedTest(1)
	@DisplayName("2. findByEnabledAndId")
	@Timeout(value = 1L, unit = TimeUnit.SECONDS)
	void read() {
		log.debug("findByEnabledAndId() invoked -- 단건 조회");
		
		Optional<Project> pjt = this.repo.findByEnabledAndId(true, 1L);
		pjt.ifPresent(foundProject -> {
			log.info("\t+ read data: {}", foundProject);
		});		
		
	}//findByEnabledAndId
	
	@Disabled
	@Tag("Project-Repository-Test")
	@Order(3)
	@Test
//	@RepeatedTest(1)
	@DisplayName("3. update")
	@Timeout(value = 1L, unit = TimeUnit.SECONDS)
	@Rollback(false)
	void update() {
		log.debug("update() invoked");
		
		Optional<Project> pjt = this.repo.findById(1L);
		pjt.ifPresent(foundProject -> {
			log.info("\t+ read data: {}", foundProject);

//			foundProject.setName("김태영");
//			foundProject.setTel("01099997777");

			log.info("\t+ after end: {}", foundProject);
		});	
		
	}//update
	
	@Disabled
	@Tag("Project-Repository-Test")
	@Order(4)
	@Test
//	@RepeatedTest(1)
	@DisplayName("4. delete")
	@Timeout(value = 1L, unit = TimeUnit.SECONDS)
	@Rollback(false)
	void delete() {
		log.debug("delete() invoked");
		
		this.repo.deleteById(1L);
		
		log.info("\t+ delete end!");
		
	}//delete
	
		
	
	
	
	@Disabled
	@Tag("Project-Repository-Test")
	@Order(10)
	@Test
//	@RepeatedTest(1)
	@DisplayName("10. list-findByEnabled")
	@Timeout(value = 1L, unit = TimeUnit.SECONDS)
	void findByEnabled() {
		log.debug("findByEnabled() invoked");
		
		int pageNo = 1;
		int pageSize = 10;		
		Pageable paging = PageRequest.of(pageNo-1, pageSize, Sort.by("crtDate").descending());
		Boolean eanbled = true;
		
		Page<Project> list = this.repo.findByEnabled(eanbled, paging);
		list.forEach(d -> log.info(d.toString()));
		
	}//findByEnabled
	
	
	
	
	
	
	
	//== contextLoads ========================
	@Disabled
	@Tag("Project-Repository-Test")
	@Order(0)
	@Test
//	@RepeatedTest(1)
	@DisplayName("ContextLoads")
	@Timeout(value = 1L, unit = TimeUnit.SECONDS)
	void contextLoads() {
		log.debug("contextLoads() invoked");
	}//contextLoads
	
	
	
	
	
	
}//end class