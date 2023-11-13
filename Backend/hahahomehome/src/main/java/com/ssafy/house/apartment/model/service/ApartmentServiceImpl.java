package com.ssafy.house.apartment.model.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.ssafy.house.apartment.model.dto.DongCode;
import com.ssafy.house.apartment.model.dto.HouseDeal;
import com.ssafy.house.apartment.model.dto.HouseDetailInfo;
import com.ssafy.house.apartment.model.dto.HouseInfo;
import com.ssafy.house.apartment.model.mapper.ApartmentMapper;
import com.ssafy.house.util.ApartPageConstant;
import com.ssafy.house.util.BoardPageConstant;

@Service
public class ApartmentServiceImpl implements ApartmentService {
	private final ApartmentMapper apartmentMapper;

	public ApartmentServiceImpl(ApartmentMapper apartmentMapper) {
		this.apartmentMapper = apartmentMapper;
	}

	@Override
	public List<String> getSido() {
		return apartmentMapper.getSido();
	}

	@Override
	public DongCode getDongCode(Map<String, String> params) {
		return apartmentMapper.getDongCode(params);
	}

	@Override
	public List<String> getGugunName(String sidoName) {
		return apartmentMapper.getGugunName(sidoName);
	}

	@Override
	public List<String> getDongNameList(String gugunName) {
		return apartmentMapper.getDongNameList(gugunName);
	}

	@Override
	public List<HouseInfo> getApartList(Map<String,Object> map) {
		int pgno=(int) map.get("page");
		int start=pgno*ApartPageConstant.LIST_SIZE-BoardPageConstant.LIST_SIZE;
		map.put("start",start);
		map.put("listSize", ApartPageConstant.LIST_SIZE);
		return apartmentMapper.getApartList(map);
	}

	@Override
	public int countApart(String dongcode) {
		return apartmentMapper.countApart(dongcode);
	}

	@Override
	public HouseDetailInfo getApartDetail(long aptCode) {
		
		return apartmentMapper.getHouseByCode(aptCode);
	}

	@Override
	public List<HouseDeal> getDeal(long aptCode) {
		
		return apartmentMapper.getDealByCode(aptCode);
	}
	
	
}
