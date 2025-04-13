package org.zerock.myapp.service;

import java.util.List;
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

@Service
public class BoardServiceImpl implements BoardService {
    @Autowired BoardRepository dao;
	
	
	@PostConstruct
    void postConstruct(){
        log.debug("BoardServiceImpl -- postConstruct() invoked");
        log.debug("dao: {}", dao);
    }//postConstruct


	@Override
	public List<Board> getAllList() {	//검색 없는 전체 리스트
		log.debug("BoardServiceImpl -- getAllList() invoked");
		
		List<Board> list = dao.findAll();
		
		return list;
	} // getAllList
	
	@Override
	public List<Board> getSearchList(BoardDTO dto) {	//검색 있는 전체 리스트
		log.debug("BoardServiceImpl -- getSearchList(()) invoked", dto);

		List<Board> list = new Vector<>();
		log.debug("리포지토리 미 생성");
		
		return list;
	} // getSearchList
	
	@Override
	public Board create(BoardDTO dto) {	//등록 처리
		log.debug("BoardServiceImpl -- create({}) invoked", dto);
		
		Board data = new Board();//dao.save(dto);
		log.debug("create data: {}", data);
		
		return data;
	} // create
	
	@Override
	public Board getById(Long id) {	// 단일 조회
		log.debug("BoardServiceImpl -- getById({}) invoked", id);
		
		//값이 존재하면 반환하고, 없으면 new Course()와 같은 기본값을 반환합니다.
		Board data = dao.findById(id).orElse(new Board());
		
		return data;
	} // getById
	
	@Override
	public Boolean update(BoardDTO dto) {//수정 처리
		log.debug("BoardServiceImpl -- update({}) invoked", dto);
		
//		Board data = dao.save(dto);
//		log.debug("create data: {}", data);
		Boolean isUpdate = true;
		return isUpdate;
	} // update

	@Override
	public Boolean deleteById(String id) { // 삭제 처리
		log.debug("BoardServiceImpl -- deleteById({}) invoked", id);
		
		//dao.deleteById(id);
		return true;
	} // deleteById
	
	
	
}//end class
