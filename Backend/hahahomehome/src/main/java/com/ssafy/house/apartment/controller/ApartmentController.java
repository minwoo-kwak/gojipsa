package com.ssafy.house.apartment.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.house.apartment.model.dto.DongCode;
import com.ssafy.house.apartment.model.service.ApartmentService;

import io.swagger.annotations.Api;

@RestController
@RequestMapping("api/v1/apartment")
@Api("아파트 정보 컨트롤로 API V1")
public class ApartmentController {

	private final ApartmentService apartmentService;

	public ApartmentController(ApartmentService apartmentService) {
		this.apartmentService = apartmentService;
	}
	
	@GetMapping("/sido")
	public ResponseEntity<List<String>> getSidoName() {
		List<String> sidoNameList = new ArrayList<String>();
		
		sidoNameList = apartmentService.getSido();
		
		return new ResponseEntity<List<String>>(sidoNameList, HttpStatus.OK);
	}
	
	@GetMapping("/gugun/{sidoName}")
	public ResponseEntity<List<String>> getGugunName(@PathVariable("sidoName") String sidoName) {
		List<String> gugunNameList = new ArrayList<String>();
		
		gugunNameList = apartmentService.getGugunName(sidoName);
		
		return new ResponseEntity<List<String>>(gugunNameList, HttpStatus.OK);
	}
	
	@GetMapping("/dongname/{gugunName}")
	public ResponseEntity<List<String>> getDongName(@PathVariable("gugunName") String gugunName) {
		List<String> dongNameList = new ArrayList<String>();
		
		dongNameList = apartmentService.getDongNameList(gugunName);
		
		return new ResponseEntity<List<String>>(dongNameList, HttpStatus.OK);
	}
	
	// 406ERROR ==> produces = "application/json"
	@GetMapping(value = "/dongcode", produces = "application/json")
	public ResponseEntity<DongCode> getDongCode(@RequestParam Map<String, String> params) {
		DongCode dongCode = new DongCode();
		
		dongCode = apartmentService.getDongCode(params);
		
		return new ResponseEntity<DongCode>(dongCode, HttpStatus.OK);
	}
}
