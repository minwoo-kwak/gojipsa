package com.ssafy.house.checklist.model.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ChecklistDetailDto {
	int chlistId;
	String apartCode;
	String apartmentName;
	int buildYear;
	String dong;
	String roadName;
	String jibun;
	String score;
	String description;
}
