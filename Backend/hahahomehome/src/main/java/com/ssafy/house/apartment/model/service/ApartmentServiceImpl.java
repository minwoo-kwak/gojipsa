package com.ssafy.house.apartment.model.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.ssafy.house.apartment.model.dto.DongCode;
import com.ssafy.house.apartment.model.mapper.ApartmentMapper;

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


	
	
	
}
