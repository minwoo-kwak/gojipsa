package com.ssafy.house.checklist.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ChecklistUpdateDto {
	
	@ApiModelProperty(value="체크리스트 아이디")
	int chlistId;	
	
	@ApiModelProperty(value="아파트 코드")
	String aptCode;		
	
	@ApiModelProperty(value="사용자 아이디")
	String userId;		
	
	@ApiModelProperty(value="체크리스트 점수")
	String score;		
	
	@ApiModelProperty(value="특이사항")
	String description;	
}
