package org.zerock.myapp.service;

import java.util.List;
import java.util.Optional;
import java.util.Vector;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.zerock.myapp.domain.BoardDTO;
import org.zerock.myapp.entity.Board;
import org.zerock.myapp.persistence.BoardRepository;

import jakarta.annotation.PostConstruct;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@NoArgsConstructor

@Service("FeedbackBoardService")
public class FeedbackServiceImpl implements BoardService {
    @Autowired BoardRepository dao;
	
	
	@PostConstruct
    void postConstruct(){
        log.debug("FeedbackServiceImpl -- postConstruct() invoked");
        log.debug("dao: {}", dao);
    }//postConstruct


	@Override
	public List<Board> getAllList() {	//검색 없는 전체 리스트
		log.debug("FeedbackServiceImpl -- getAllList() invoked");
		
		List<Board> list = dao.findAll();
		
		return list;
	} // getAllList
	
	@Override
	public List<Board> getSearchList(BoardDTO dto) {	//검색 있는 전체 리스트
		log.debug("FeedbackServiceImpl -- getSearchList(()) invoked", dto);

		List<Board> list = new Vector<>();
		log.debug("리포지토리 미 생성");
		
		return list;
	} // getSearchList
	
	@Override
	public Board create(BoardDTO dto) {	//등록 처리
		log.debug("FeedbackServiceImpl -- create({}) invoked", dto);
		
		Board data = new Board();//dao.save(dto);
		try {
		
		data.setId(dto.getId()); // 게시판 Id
		data.setTitle(dto.getTitle()); // 제목
		data.setDetail(dto.getDetail()); // 내용
		data.setCrtDate(dto.getCrtDate()); // 작성일
		data.setCount(dto.getCount()); // 조회수
		
		dao.save(data);
		log.debug("create data: {}", data);
		} catch (Exception e) {
			throw new IllegalArgumentException("게시글 등록이 실패했습니다. 다시 시도해주세요.");
		}
		
		
		
		return data;
	} // create
	
	@Override
	public Board getById(Long id) {	// 단일 조회
		log.debug("FeedbackServiceImpl -- getById({}) invoked", id);
		
		//값이 존재하면 반환하고, 없으면 new Course()와 같은 기본값을 반환합니다.
		Optional<Board> optional = dao.findById(id);
		if (optional.isPresent()) {
			log.debug("Found: {}", optional.get());
			return optional.get();
		} else {
			log.warn("No employee selected: {}", id);
			return null;
		}
	} // getById
	
	@Override
	public Boolean update(Long id, BoardDTO dto) {//수정 처리
		log.debug("FeedbackServiceImpl -- update({}) invoked", dto);

		Board post = dao.findById(dto.getId())
	            .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시글입니다."));
		
		try {
			post.setTitle(dto.getTitle()); // 게시글 제목
			post.setDetail(dto.getDetail()); // 게시글 내용
	      
	      dao.save(post);
	      return true; // db에 저장.
	      } catch (Exception e) {
	         throw new IllegalArgumentException("게시글 수정에 실패했습니다. 다시 시도해 주세요.");
	      }
	} // update

	@Override
	public Boolean deleteById(String id) { // 삭제 처리
		log.debug("FeedbackServiceImpl -- deleteById({}) invoked", id);
		
		//dao.deleteById(id);
		return true;
	}
	
	
}//end class
