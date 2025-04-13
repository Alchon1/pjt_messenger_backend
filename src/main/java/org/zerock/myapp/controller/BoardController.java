package org.zerock.myapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.zerock.myapp.entity.Board;
import org.zerock.myapp.service.BoardService;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;


/**
 * 게시판(공지사항 + 건의사항) Controller
 */

@Slf4j
@NoArgsConstructor

@RequestMapping("/board")
@RestController
public class BoardController {
	
	@Autowired BoardService boardService;
	
	@GetMapping
	List<Board> list() { // 리스트
		log.debug("list() invoked.");
		
		List<Board> list = boardService.getAllList();
		
		return list;
	} // list
	
	@PostMapping
	String register() { // 등록 처리
		log.debug("register() invoked.");
		
		return "register";
	} // register
	
	
	@GetMapping(path = "/{id}")
	Board read( // 세부 조회
			@PathVariable Long id ) {
		log.debug("read({}) invoked.",id);
		
		Board read = boardService.getById(id);
		
		return read;
	} // read
	
	@PutMapping(path = "/{id}")
	String update( // 수정 처리
			@PathVariable Long id
			) { 
		log.debug("update({}) invoked.",id);
		
		return "update";
	} // update
	
	@DeleteMapping(path = "/{id}")
	String delete( // 삭제 처리
			@PathVariable Long id
			) {
		log.debug("delete({}) invoked.",id);
		
		return "delete";
	} // delete
	

} // end class
