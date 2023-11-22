package com.ssafy.house.apartment.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class HouseDeal {
	
	@ApiModelProperty(value="거래 금액")
	String dealAmount;
	
	@ApiModelProperty(value="거래 연도")
	int dealYear;
	
	@ApiModelProperty(value="거래 월")
	int dealMonth;
	
	@ApiModelProperty(value="거래 일")
	int dealDay;
} 
