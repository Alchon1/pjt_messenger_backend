package org.zerock.myapp.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.zerock.myapp.domain.BoardDTO;
import org.zerock.myapp.entity.Board;
import org.zerock.myapp.entity.Employee;
import org.zerock.myapp.exception.ServiceException;
import org.zerock.myapp.persistence.BoardRepository;
import org.zerock.myapp.persistence.EmployeeRepository;

import jakarta.annotation.PostConstruct;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@NoArgsConstructor

@Service("FeedbackBoardService")
public class FeedbackServiceImpl implements BoardService {
    @Autowired BoardRepository dao;
    @Autowired EmployeeRepository edao;
	
	@PostConstruct
    void postConstruct(){
        log.debug("FeedbackServiceImpl -- postConstruct() invoked");
        log.debug("dao: {}", dao);
    }//postConstruct


	@Override
	public Page<Board> getSearchList(BoardDTO dto, Pageable paging) {	//검색 있는 전체 리스트
		log.debug("BoardServiceImpl -- getSearchList(()) invoked", dto);
		
		if(dto.getSearchWord() != null && dto.getSearchWord().length() == 0) dto.setSearchWord(null);
		if(dto.getSearchText() != null && dto.getSearchText().length() == 0) dto.setSearchText(null);

		if (dto.getSearchText() == null) {
			// 검색 리스트: 활성화상태(true)
			return this.dao.findByEnabledAndType(true, dto.getType(), paging);

		} 
		else if (dto.getSearchText() != null) {
			return switch (dto.getSearchWord()) {
			case "name" -> this.dao.findByEnabledAndTypeAndTitleContaining(true, dto.getType(), dto.getSearchText(), paging);
			default -> throw new IllegalArgumentException("swich_1 - Invalid search word: " + dto.getSearchWord());
			};

		}
		return null;
	} // getSearchList
	
	@Override
	public Board create(BoardDTO dto) {	//등록 처리
		log.debug("BoardServiceImpl -- create({}) invoked", dto);
		
		Board data = new Board();//dao.save(dto);
		try {
			Employee employee = edao.findById(dto.getAuthorEmpno())
					.orElseThrow(() -> new IllegalArgumentException("유효하지 않은 사원 ID입니다."));
			
			data.setEmployee(employee);//임시
			data.setType(2);
				
			data.setTitle(dto.getTitle()); // 제목
			data.setPosition(dto.getPosition());
			data.setCount(0); // 조회수
			data.setDetail(dto.getDetail()); // 내용
			data.setEnabled(true);
			
			dao.save(data);
			log.debug("create data: {}", data);
		} catch (Exception e) {
			throw new IllegalArgumentException("게시글 등록이 실패했습니다. 다시 시도해주세요.");
		}
				
		return data;
	} // create
	
	@Override
	public Board getById(Long id) {	// 단일 조회
		log.debug("BoardServiceImpl -- getById({}) invoked", id);
		
		//값이 존재하면 반환하고, 없으면 new Course()와 같은 기본값을 반환합니다.
		Optional<Board> optional = dao.findById(id);
		if (optional.isPresent()) {
			Board board = optional.get();
			log.debug("Found: {}", optional.get());
			
			board.setCount(board.getCount() + 1);
			dao.save(board);
			
			return board;
		} else {
			log.warn("No employee selected: {}", id);
			return null;
		}
	} // getById
	
	@Override
	public Board update(Long id, BoardDTO dto) {//수정 처리
		log.debug("BoardServiceImpl -- update({}) invoked", dto);

		Board post = dao.findById(dto.getId())
	            .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시글입니다."));
		
		try {
			post.setTitle(dto.getTitle()); // 게시글 제목
			post.setDetail(dto.getDetail()); // 게시글 내용
	      
	      return dao.save(post); // db에 저장.
	      } catch (Exception e) {
	         throw new IllegalArgumentException("게시글 수정에 실패했습니다. 다시 시도해 주세요.");
	      }
	} // update

	@Override
	public Board deleteById(Long id) throws ServiceException { // 삭제 처리
		log.debug("BoardServiceImpl -- deleteById({}) invoked", id);

		try {
			Optional<Board> optionalBoard = this.dao.findById(id);
	
			if (optionalBoard.isPresent()) {
				Board board = optionalBoard.get();
				board.setEnabled(false);
	
				Board result = this.dao.save(board);
				log.info("Delete success");
	
				return result;
			} // if
		}  catch (Exception e) {
			throw new ServiceException("프로젝트 삭제 중 오류가 발생했습니다.", e);
		}
		return null;
	} // delete
	
	
}//end class
