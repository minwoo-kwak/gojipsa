package com.ssafy.house.checklist.model.service;

import java.util.List;
import java.util.Map;

import com.ssafy.house.checklist.model.dto.ChecklistDetailDto;
import com.ssafy.house.checklist.model.dto.ChecklistSummaryDto;
import com.ssafy.house.checklist.model.dto.ChecklistUpdateDto;
import com.ssafy.house.checklist.model.dto.ChecklistWriteDto;

public interface ChecklistService {
	// 사용자가 작성한 checklist 가져오기(R)
	List<ChecklistSummaryDto> getAllChecklistById(Map<String,Object> map);
	
	// 사용자가 작성한 checklist 전체 개수
	long countChecklist(String userId);
	
	// 사용자가 작성한 checklist 세부 내용 가져오기(R)
	ChecklistDetailDto getDetailChecklist(Map<String,Object> map);
	
	// 사용자 checklist 작성하기(C)
	int writeChecklist(ChecklistWriteDto dto);
	
	// 사용자 checklist 수정하기(U)
	int updateChecklist(ChecklistUpdateDto dto);
	
	// 사용자 checklist 삭제하기(D)
	int deleteChecklist(Map<String,Object> map);
}
