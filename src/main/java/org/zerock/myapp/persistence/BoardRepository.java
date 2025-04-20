package org.zerock.myapp.persistence;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.zerock.myapp.entity.Board;
import org.zerock.myapp.entity.Employee;


/**
 * 게시판(공지사항 + 건의사항) Repository
 */

@Repository
public interface BoardRepository extends JpaRepository<Board, Long>, JpaSpecificationExecutor<Board> {
	
//	Page<Board> findByTitleContainingAndEnabledTrue(String title, Pageable pageable);
//	Page<Board> findBoardByEmployeeName(String name, Pageable pageable);


	//게시판 별 유효한 전체 게시물
	public abstract Page<Board> findByEnabledAndType(Boolean enabled, Integer type, Pageable paging);
	
	//게시판 별 유효한 전체 게시물 + 이름
	public abstract Page<Board> findByEnabledAndTypeAndTitleContaining(Boolean enabled, Integer type, String Title, Pageable paging);
	
	//게시판 별 유효한 전체 게시물 + 작성자
	final String nativeSQL_board_empname = """
			SELECT B.*
			FROM T_BOARD B
			   LEFT JOIN T_EMPLOYEE E ON B.EMPNO = E.EMPNO
			WHERE B.ENABLED = :enabled AND B.TYPE = :type AND E.NAME '%' || :empName || '%'
		""";
	@Query(value = nativeSQL_board_empname, nativeQuery = true)
	Page<Board> findBoardByEmployeeName(
			@Param("enabled") Boolean enabled, 
			@Param("type") Integer type, 
			@Param("empName") String empName, 
			Pageable paging
		);
	
	
	
	
	public abstract Optional<Board> findByTitle(String title);
	public abstract Optional<Board> findByEmployee (Employee employee);
	public abstract Optional<Board> findByType(Integer Type);
	
} // end interface
