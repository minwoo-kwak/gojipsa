package com.ssafy.house.info.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.house.info.model.dto.PsyIndexDto;
import com.ssafy.house.info.model.service.InfoService;

@RestController
@RequestMapping("api/v1/info")
public class InfoController {
	
	private final InfoService infoService;

	public InfoController(InfoService infoService) {
		this.infoService = infoService;
	}
	
	@GetMapping("/list")
	public ResponseEntity<List<PsyIndexDto>> getPsyIndexList() {
		
		List<PsyIndexDto> list = infoService.getPsyIndexList();
		
		return new ResponseEntity<List<PsyIndexDto>>(list, HttpStatus.OK);
	}

}
