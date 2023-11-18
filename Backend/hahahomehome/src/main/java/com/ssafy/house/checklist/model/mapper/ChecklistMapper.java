package com.ssafy.house.checklist.model.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.house.checklist.model.dto.ChecklistDetailDto;
import com.ssafy.house.checklist.model.dto.ChecklistSummaryDto;
import com.ssafy.house.checklist.model.dto.ChecklistUpdateDto;
import com.ssafy.house.checklist.model.dto.ChecklistWriteDto;

@Mapper
public interface ChecklistMapper {
	
	List<ChecklistSummaryDto> showSummaryChecklist(Map<String,Object> map);
	long countChecklist(String userId);
	ChecklistDetailDto showDetailChecklist(Map<String,Object> map);
	int updateChecklist(ChecklistUpdateDto dto);
	int writeChecklist(ChecklistWriteDto dto);
	int deleteChecklist(Map<String,Object> map);
}
