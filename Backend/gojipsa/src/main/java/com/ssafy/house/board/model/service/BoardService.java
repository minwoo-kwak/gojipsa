package com.ssafy.house.board.model.service;

import java.util.List;
import java.util.Map;

import com.ssafy.house.board.model.dto.BoardDto;

public interface BoardService {
	List<BoardDto> getAllBoard(String pgNo);
	BoardDto getDetailBoard(int id);
	public int increaseHit(int id);
	int writeBoard(BoardDto boardDto);
	int modifyBoard(BoardDto boardDto);
	int deleteBoard(int boardNo);
	long countBoard();
}
