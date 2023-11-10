package com.ssafy.house.apartment.model.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.ssafy.house.apartment.model.dto.DongCode;


public interface ApartmentService {

	public DongCode getDongCode(Map<String, String> params);

	public List<String> getSido();

	public List<String> getGugunName(String sidoName);

	public List<String> getDongNameList(String gugunName);

}
