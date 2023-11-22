package com.ssafy.house.board.model.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.ssafy.house.board.model.dto.BoardDto;
import com.ssafy.house.board.model.mapper.BoardMapper;
import com.ssafy.house.util.BoardPageConstant;

@Service
public class BoardServiceImpl implements BoardService{
	
	private final BoardMapper boardMapper;
	
	public BoardServiceImpl(BoardMapper boardMapper) {
		this.boardMapper = boardMapper;
	}

	@Override
	public List<BoardDto> getAllBoard(String pgNo) {
		
		Map<String,Object> param=new HashMap<String, Object>();
		int pgno=Integer.parseInt(pgNo==null?"1":pgNo);
		int start=pgno*BoardPageConstant.LIST_SIZE-BoardPageConstant.LIST_SIZE;
		param.put("start",start);
		param.put("listSize",BoardPageConstant.LIST_SIZE);
		
		return boardMapper.getAllBoard(param);
	}

	@Override
	public BoardDto getDetailBoard(int id) {
		return boardMapper.selectById(id);
	}
	
	@Override
	public int increaseHit(int id) {
		return boardMapper.increaseHit(id);
	}

	@Override
	public int writeBoard(BoardDto boardDto) {
		return boardMapper.insertBoard(boardDto);
	}

	@Override
	public int modifyBoard(BoardDto boardDto) {
		return boardMapper.modifyBoard(boardDto);
	}

	@Override
	public int deleteBoard(int boardNo) {
		return boardMapper.deleteBoard(boardNo);
	}

	@Override
	public long countBoard() {
		return boardMapper.countBoard();
	}

}
