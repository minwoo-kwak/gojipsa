package com.ssafy.house.apartment.model.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.house.apartment.model.dto.DongCode;

@Mapper
public interface ApartmentMapper {
	
	List<String> getSido();

	DongCode getDongCode(Map<String, String> params);

	List<String> getGugunName(String sidoName);
	
	List<String> getDongNameList(String gugunName);

}
