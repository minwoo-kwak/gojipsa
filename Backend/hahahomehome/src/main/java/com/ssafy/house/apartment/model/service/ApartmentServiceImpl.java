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
	public List<String> getDongNameList(Map<String, String> map) {
		System.out.println("가져온 동네임" + apartmentMapper.getDongNameList(map));
		return apartmentMapper.getDongNameList(map);
	}

	@Override
	public List<HouseInfo> getApartList(Map<String,Object> map) {
		Map<String,Object> paramMap=new HashMap<>();
		paramMap.put("dongcode", map.get("dongcode"));
		// 페이지 번호
		int pgno=(int) map.get("page");
		
		// 페이지 번호로 데이터를 가져올 시작 지점 구하기
		int start=pgno*ApartPageConstant.LIST_SIZE-ApartPageConstant.LIST_SIZE;
		
		// 계산 결과를 map에 담아 mapper로 보냄
		paramMap.put("start",start);
		paramMap.put("listSize", ApartPageConstant.LIST_SIZE);
		
		return apartmentMapper.getApartList(paramMap);
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
