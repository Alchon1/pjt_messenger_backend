package org.zerock.myapp.service;

import java.util.List;

import org.zerock.myapp.domain.BoardDTO;
import org.zerock.myapp.entity.Board;

public interface BoardService {
	
	public abstract List<Board> getAllList();     			// 전체 조회
	public abstract List<Board> getSearchList(BoardDTO dto); // 전체 조회(검색)
	
	public abstract Board create(BoardDTO dto);    	// 생성 처리
	public abstract Board getById(Long id);    // 단일 조회
	public abstract Boolean update(BoardDTO dto);    	// 수정 처리
	public abstract Boolean deleteById(String id);// 삭제 처리
	
}//end interface