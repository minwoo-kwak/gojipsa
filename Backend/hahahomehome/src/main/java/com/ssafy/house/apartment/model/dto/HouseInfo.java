package com.ssafy.house.apartment.model.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class HouseInfo {
	long aptCode;
	String dongCode;
	String apartmentName;
	int buildYear;
	String dong;
	String roadName;
	String jibun;
	String lat;
	String lng;
}
