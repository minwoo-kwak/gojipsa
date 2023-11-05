package com.ssafy.house.model.service;

import java.util.List;
import java.util.Map;

import com.ssafy.house.model.dto.BoardDto;

public interface BoardService {
	List<BoardDto> getAllBoard(String pgNo);
	BoardDto getDetailBoard(int id);
	int writeBoard(BoardDto boardDto);
	int modifyBoard(BoardDto boardDto);
	int deleteBoard(int boardNo);
}
