package com.ssafy.house.checklist.model.service;

import com.ssafy.house.checklist.model.dto.ChecklistWriteDto;

public interface ChecklistService {
	// 사용자가 작성한 checklist 가져오기(R)
	// 필요한 정보(아파트 코드, 아파트 이름, 아파트 위치)
		
	// 사용자가 작성한 checklist 세부 내용 가져오기(R)
	// 필요한 정보 (아파트 코드, 아파트 이름, 아파트 위치, 사용자가 입력한 값 정보)
		
	// 사용자 checklist 작성하기(C)
	// 필요한 값: 아파트 코드, 사용자 id, 사용자가 입력한 값(score, description)
	int writeChecklist(ChecklistWriteDto dto);
	
	// 사용자 checklist 수정하기(U)
	//int updateChecklist(ChecklistWriteDto dto);
	
	// 사용자 checklist 삭제하기(D)
}
