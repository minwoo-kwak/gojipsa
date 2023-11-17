package com.ssafy.house.checklist.model.service;

import org.springframework.stereotype.Service;

import com.ssafy.house.checklist.model.dto.ChecklistWriteDto;
import com.ssafy.house.checklist.model.mapper.ChecklistMapper;

@Service
public class ChecklistServiceImpl implements ChecklistService{
	
	private final ChecklistMapper checklistMapper;
	
	public ChecklistServiceImpl(ChecklistMapper checklistMapper) {
		this.checklistMapper = checklistMapper;
	}

	@Override
	public int writeChecklist(ChecklistWriteDto dto) {
		return checklistMapper.writeChecklist(dto);
	}

}
