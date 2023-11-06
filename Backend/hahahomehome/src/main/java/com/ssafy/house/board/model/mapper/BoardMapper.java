package com.ssafy.house.board.model.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.house.board.model.dto.BoardDto;

@Mapper
public interface BoardMapper {
	List<BoardDto> getAllBoard(Map<String,Object> param);
	BoardDto selectById(int id);
	int insertBoard(BoardDto boardDto);
	int modifyBoard(BoardDto boardDto);
	int deleteBoard(int boardNo);
	// 전체 게시글 개수
	long countBoard();
}
