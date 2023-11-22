package com.ssafy.house.checklist.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ChecklistSummaryDto {
	
	@ApiModelProperty(value="체크리스트 아이디")
	int chlistId;
	
	@ApiModelProperty(value="아파트 코드")
	String aptCode;
	
	@ApiModelProperty(value="아파트 이름")
	String apartmentName;
	
	@ApiModelProperty(value="건축 연도")
	int buildYear;
	
	@ApiModelProperty(value="동 이름")
	String dong;
	
	@ApiModelProperty(value="도로명")
	String roadName;
	
	@ApiModelProperty(value="지번")
	String jibun;
}
