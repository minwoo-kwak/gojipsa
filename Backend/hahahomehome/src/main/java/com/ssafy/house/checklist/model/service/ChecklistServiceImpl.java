package com.ssafy.house.checklist.model.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.ssafy.house.checklist.model.dto.ChecklistDetailDto;
import com.ssafy.house.checklist.model.dto.ChecklistSummaryDto;
import com.ssafy.house.checklist.model.dto.ChecklistUpdateDto;
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

	@Override
	public List<ChecklistSummaryDto> getAllChecklistById(Map<String,Object> map) {
		return checklistMapper.showSummaryChecklist(map);
	}

	@Override
	public ChecklistDetailDto getDetailChecklist(Map<String, Object> map) {
		return checklistMapper.showDetailChecklist(map);
	}

	@Override
	public int updateChecklist(ChecklistUpdateDto dto) {
		return checklistMapper.updateChecklist(dto);
	}

	@Override
	public int deleteChecklist(Map<String, Object> map) {
		return checklistMapper.deleteChecklist(map);
	}

	@Override
	public long countChecklist(String userId) {
		return checklistMapper.countChecklist(userId);
	}

}
