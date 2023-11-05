package com.ssafy.house.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.house.model.dto.BoardDto;
import com.ssafy.house.model.dto.BoardPaginationResponse;
import com.ssafy.house.model.service.BoardService;
import com.ssafy.house.util.BoardPageConstant;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/board")
public class BoardController {
	
	// Service
	private final BoardService boardService;
	
	public BoardController(BoardService boardService) {
		this.boardService = boardService;
	}
	
	// 전체 게시글 가져오기
	
	@ApiOperation(value="공지사항 정보 목록 가져오기", 
			notes="등록된 공지사항 글 목록을 가져옵니다."
			)
	@ResponseBody
	@GetMapping(path="/list")
	public ResponseEntity<?> listBoard(@RequestParam(value="page",required=false) String pageNo){
		System.out.println(pageNo);
		List<BoardDto> boardList= (List<BoardDto>)boardService.getAllBoard(pageNo);
		return new ResponseEntity<List<BoardDto>>(boardList,HttpStatus.OK);

	}
	
	
	// 상세 게시글 가져오기
	@ResponseBody
	@GetMapping("/{id}")
	public ResponseEntity<Map<String,Object>> detailBoard(@PathVariable("id") int id){
		
		BoardDto boardInfo=(BoardDto)boardService.getDetailBoard(id);
		
		// board_no가==id인 게시글을 찾을 수 없는 경우
		if (boardInfo==null) {
			return ResponseEntity.notFound().build();
		}
		// 해당 게시글이 있는 경우
		Map<String,Object> result=new HashMap<>();
		result.put("data", boardInfo);
		return ResponseEntity.ok().body(result);
	}
	
	// 게시글 등록하기
	@ResponseBody
	@PostMapping("/")
	public ResponseEntity<Map<String,Object>> writeBoard(@RequestBody BoardDto boardDto) {
		int resultCode=boardService.writeBoard(boardDto);
		
		// 게시글이 생성된 경우
		if (resultCode==1) {
			return ResponseEntity.status(HttpStatus.CREATED).build();
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
	}
	
	// 게시글 수정하기
	@ResponseBody
	@PutMapping("/")
	public ResponseEntity<Map<String,Object>> modifyBoard(@RequestBody BoardDto boardDto){
		
		int resultCode=boardService.modifyBoard(boardDto);
		
		// 게시글이 수정된 경우
		if (resultCode==1) {
			return ResponseEntity.status(HttpStatus.OK).build();
		}
		return ResponseEntity.status(HttpStatus.NOT_MODIFIED).build();
	}
	
	// 게시글 삭제하기
	@ResponseBody
	@DeleteMapping("/{id}")
	public ResponseEntity<Map<String,Object>> deleteBoard(@PathVariable("id") int boardNo){
		
		int resultCode=boardService.deleteBoard(boardNo);
		
		// 게시글이 삭제된 경우
		if (resultCode==1) {
			return ResponseEntity.status(HttpStatus.OK).build();
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	}
	
}
