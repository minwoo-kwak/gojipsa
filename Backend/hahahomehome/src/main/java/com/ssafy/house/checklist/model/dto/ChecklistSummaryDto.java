package com.ssafy.house.checklist.model.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ChecklistSummaryDto {
	int chlistId;
	String aptCode;
	String apartmentName;
	int buildYear;
	String dong;
	String roadName;
	String jibun;
}
