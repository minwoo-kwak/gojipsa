package com.ssafy.house.apartment.model.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.house.apartment.model.dto.DongCode;
import com.ssafy.house.apartment.model.dto.HouseDeal;
import com.ssafy.house.apartment.model.dto.HouseDetailInfo;
import com.ssafy.house.apartment.model.dto.HouseInfo;

@Mapper
public interface ApartmentMapper {
	
	List<String> getSido();

	DongCode getDongCode(Map<String, String> params);

	List<String> getGugunName(String sidoName);
	
	List<String> getDongNameList(Map<String, String> map);

	List<HouseInfo> getApartList(Map<String, Object> map);
	
	int countApart(String dongcode);
	
	HouseDetailInfo getHouseByCode(long aptCode);
	
	List<HouseDeal> getDealByCode(long aptCode);
}
