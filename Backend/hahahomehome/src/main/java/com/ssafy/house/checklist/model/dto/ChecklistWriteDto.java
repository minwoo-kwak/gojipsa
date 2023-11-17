package com.ssafy.house.checklist.model.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ChecklistWriteDto {
	String aptCode;		// 아파트 코드
	String userId;		// 사용자 아이디
	String score;		// 사용자가 입력한 값
	String description;	// 사용자가 입력한 텍스트
}
