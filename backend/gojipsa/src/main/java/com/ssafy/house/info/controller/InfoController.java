package com.ssafy.house.info.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.house.info.model.dto.PsyIndexDto;
import com.ssafy.house.info.model.service.InfoService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("api/v1/info")
@Api(tags= {"부동산 소비자심리지수 컨트롤러 API V1"})
public class InfoController {
	
	private final InfoService infoService;

	public InfoController(InfoService infoService) {
		this.infoService = infoService;
	}
	
	/**
	 * 부동산 소비자 심리지수를 가져옵니다.
	 * @return 부동산 소비자 심리지수 리스트
	 */
	@GetMapping("/list")
	@ApiOperation(value = "부동산 소비자심리지수 가져오기", notes = "월별 부동산 소비자심리지수 리스트를 가져옵니다.")
	public ResponseEntity<List<PsyIndexDto>> getPsyIndexList() {
		
		List<PsyIndexDto> list = infoService.getPsyIndexList();
		
		return new ResponseEntity<List<PsyIndexDto>>(list, HttpStatus.OK);
	}

}
