package com.ssafy.house.apartment.model.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.ssafy.house.apartment.model.dto.DongCode;
import com.ssafy.house.apartment.model.dto.HouseDeal;
import com.ssafy.house.apartment.model.dto.HouseDetailInfo;
import com.ssafy.house.apartment.model.dto.HouseInfo;


public interface ApartmentService {

	public DongCode getDongCode(Map<String, String> params);

	public List<String> getSido();

	public List<String> getGugunName(String sidoName);

	public List<String> getDongNameList(Map<String,String> map);
	
	public List<HouseInfo> getApartList(Map<String,Object> map);

	public int countApart(String dongcode);
	
	public HouseDetailInfo getApartDetail(long aptCode);
	
	public List<HouseDeal> getDeal(long aptCode);
}
