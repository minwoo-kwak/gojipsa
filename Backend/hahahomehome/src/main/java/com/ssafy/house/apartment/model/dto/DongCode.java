package com.ssafy.house.apartment.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class DongCode {
	
	@ApiModelProperty(value="동 코드")
	String dongCode;
	
	@ApiModelProperty(value="동 이름")
	String dongName;
	
	@ApiModelProperty(value="구/군 이름")
	String gugunName;
	
	@ApiModelProperty(value="시/도 이름")
	String sidoName;
	
	
}
