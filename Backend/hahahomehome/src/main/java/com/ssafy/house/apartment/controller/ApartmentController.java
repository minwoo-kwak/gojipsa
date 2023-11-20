package com.ssafy.house.apartment.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.house.apartment.model.dto.DongCode;
import com.ssafy.house.apartment.model.dto.HouseDeal;
import com.ssafy.house.apartment.model.dto.HouseDetailInfo;
import com.ssafy.house.apartment.model.dto.HouseInfo;
import com.ssafy.house.apartment.model.service.ApartmentService;
import com.ssafy.house.util.ApartPageConstant;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping("api/v1/apartment")
@Api("아파트 정보 컨트롤러 API V1")
public class ApartmentController {

	private final ApartmentService apartmentService;

	public ApartmentController(ApartmentService apartmentService) {
		this.apartmentService = apartmentService;
	}
	
	@GetMapping("/sido")
	@ApiOperation(value="시/도 이름 받기", notes="시/도 이름을 받는 API")
	public ResponseEntity<List<String>> getSidoName() {
		List<String> sidoNameList = new ArrayList<String>();
		
		sidoNameList = apartmentService.getSido();
		
		return new ResponseEntity<List<String>>(sidoNameList, HttpStatus.OK);
	}
	
	@GetMapping("/gugun/{sidoName}")
	@ApiOperation(value="구/군 이름 받기",notes="시/도 이름으로 구/군 이름을 받는 API")
	@ApiImplicitParam(name="sidoName",value="시/도 이름")
	public ResponseEntity<List<String>> getGugunName(@PathVariable("sidoName") String sidoName) {
		List<String> gugunNameList = new ArrayList<String>();
		
		gugunNameList = apartmentService.getGugunName(sidoName);
		
		return new ResponseEntity<List<String>>(gugunNameList, HttpStatus.OK);
	}
	
	@GetMapping("/dongname")
	@ApiOperation(value="동 이름 받기", notes="구/군 이름으로 동 이름을 받는 API")
	public ResponseEntity<List<String>> getDongName(@RequestParam Map<String, String> map) {
		System.out.println("동 이름 받기 map" + map);
		
		List<String> dongNameList = new ArrayList<String>();
		
		dongNameList = apartmentService.getDongNameList(map);
		
		return new ResponseEntity<List<String>>(dongNameList, HttpStatus.OK);
	}
	
	// 406ERROR ==> produces = "application/json"
	@GetMapping(value = "/dongcode", produces = "application/json")
	@ApiOperation(value="동 코드 받기", notes="시/도, 구/군, 동 이름으로 동 코드를 받는 API")
	public ResponseEntity<DongCode> getDongCode(@RequestParam Map<String, String> params) {
		DongCode dongCode = new DongCode();
		
		dongCode = apartmentService.getDongCode(params);
		
		return new ResponseEntity<DongCode>(dongCode, HttpStatus.OK);
	}
	
	// dongCode로 아파트 전체 정보 얻기
	@GetMapping("/{dongcode}")
	@ApiOperation(value="동 코드에 해당하는 아파트 리스트 받기", notes="특정 동코드 아파트 리스트를 받는 API")
	public ResponseEntity<?> getApartList(@PathVariable("dongcode") @ApiParam(value = "동코드", required = true) String dongcode,
			@RequestParam(value="page",required=false) @ApiParam(value = "페이지 번호", required = false) String pageNo){
		
		Map<String,Object> response=new HashMap<>();
		
		Map<String,Object> map= new HashMap<>();
		map.put("dongcode", dongcode);
		int pgno=pageNo==null?1:Integer.parseInt(pageNo);
		map.put("page",pgno);
		map.put("listSize",ApartPageConstant.LIST_SIZE);
		
		// 아파트 목록
		List<HouseInfo> apartList=apartmentService.getApartList(map);
		
		// 페이지 정보를 담을 HashMap
		HashMap<String,Object> pageInfo=new HashMap<>();
		// 전체 페이지 수
		long totalApartCnt=apartmentService.countApart(dongcode);
		long totalPageCnt=(long)Math.ceil((totalApartCnt*1.0)/ApartPageConstant.LIST_SIZE);
		pageInfo.put("totalPageCnt", totalPageCnt);
		
		// 페이지 그룹
		int pageGroup=(int) Math.ceil(pgno*1.0/ApartPageConstant.NAVIGATION_SIZE);
		
		// 첫 번째 페이지 번호
		long startPage=((pageGroup-1)*ApartPageConstant.LIST_SIZE)+1;
		pageInfo.put("startPage",startPage);
		
		// 화면에 보여질 마지막 페이지 번호
		long lastPage=(pageGroup)*ApartPageConstant.LIST_SIZE;
		if(lastPage>totalPageCnt) lastPage=totalPageCnt;
		pageInfo.put("lastPage",lastPage);
		
		// Navigation Bar에 보여질 페이지 개수
		pageInfo.put("navigationSize", ApartPageConstant.NAVIGATION_SIZE);
		
		// 아파트 리스트 정보와 pagination 정보를 담는다.
		response.put("data",apartList);
		response.put("pageInfo",pageInfo);
		
		return ResponseEntity.ok().body(response);
	}
	
	// 아파트 상세 정보 얻기
	@GetMapping("/detail/{aptCode}")
	@ApiOperation(value="아파트 상세 정보 받기", notes="특정 아파트 코드로 아파트의 상세 정보를 받는 API")
	public ResponseEntity<HouseDetailInfo> getApartDetail(@PathVariable("aptCode") @ApiParam(value = "아파트 코드", required = true) long aptCode){
		
		HouseDetailInfo info = apartmentService.getApartDetail(aptCode);
		
		return new ResponseEntity<HouseDetailInfo>(info, HttpStatus.OK);
	}
	
	// 특정 아파트 거래 내역 얻기(오래된 순서대로)
	@GetMapping("/deal/{aptCode}")
	@ApiOperation(value="아파트 거래 금액 받기",notes="특정 아파트 코드로 아파트 거래액을 시간 순대로 받는 API")
	public ResponseEntity<List<HouseDeal>> getApartDeal(@PathVariable("aptCode") @ApiParam(value = "아파트 코드", required = true) long aptCode){
		List<HouseDeal> deal=apartmentService.getDeal(aptCode);
		
		return new ResponseEntity<>(deal,HttpStatus.OK);
	}
}
