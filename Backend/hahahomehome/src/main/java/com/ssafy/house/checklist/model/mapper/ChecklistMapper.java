package com.ssafy.house.checklist.model.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.house.checklist.model.dto.ChecklistDetailDto;
import com.ssafy.house.checklist.model.dto.ChecklistSummaryDto;
import com.ssafy.house.checklist.model.dto.ChecklistWriteDto;

@Mapper
public interface ChecklistMapper {
	
	List<ChecklistSummaryDto> showSummaryChecklist(String string);
	List<ChecklistDetailDto> showDetailChecklist(Map<String,String> map);
	// int updateChecklist(ChecklistWriteDto dto);
	int writeChecklist(ChecklistWriteDto dto);
}
