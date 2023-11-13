package com.ssafy.house.apartment.model.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class HouseDetailInfo {
	long aptCode;
	int buildYear;
	String roadName;
	String roadNameCode;
	String dong;
	String dongCode;
	String apartmentName;
	String jibun;
	String lng;
	String lat;
}
