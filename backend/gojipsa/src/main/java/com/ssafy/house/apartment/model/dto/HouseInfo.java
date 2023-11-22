package com.ssafy.house.apartment.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class HouseInfo {
	
	@ApiModelProperty(value="아파트 코드")
	long aptCode;
	
	@ApiModelProperty(value="동 코드")
	String dongCode;
	
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
	
	@ApiModelProperty(value="위도")
	String lat;
	
	@ApiModelProperty(value="경도")
	String lng;
}
