package com.ssafy.house.checklist.model.service;

import java.util.HashMap;
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
		Map<String, Object> map = new HashMap<>();
		map.put("userId", dto.getUserId());
		map.put("aptCode", dto.getAptCode());
		
		Integer chlistId = checklistMapper.getChlistId(map);
		// 신규 등록일 경우
		if(chlistId == null) {
			return checklistMapper.writeChecklist(dto);
		} // 신규 등록이 아닐 경우
		else {
			ChecklistUpdateDto updateDto = new ChecklistUpdateDto();
			updateDto.setChlistId(chlistId);
			updateDto.setAptCode(dto.getAptCode());
			updateDto.setDescription(dto.getDescription());
			updateDto.setScore(dto.getScore());
			updateDto.setUserId(dto.getUserId());
			return checklistMapper.updateChecklist(updateDto);
		}
		
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

	@Override
	public Map<String, Object> loadChecklistContent(Map<String, Object> map) {
		return checklistMapper.loadChecklistContent(map);
	}

}
