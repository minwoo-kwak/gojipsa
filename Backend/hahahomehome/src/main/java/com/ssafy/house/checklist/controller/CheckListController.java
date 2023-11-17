package com.ssafy.house.checklist.controller;

import java.util.Base64;

import javax.servlet.http.HttpServletRequest;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.house.checklist.model.dto.ChecklistWriteDto;
import com.ssafy.house.checklist.model.service.ChecklistService;

@RestController
@RequestMapping("api/v1/checklist")
public class CheckListController {
	
	private final ChecklistService checklistService;

	public CheckListController(ChecklistService checklistService) {
		this.checklistService = checklistService;
	}
	
	// 사용자가 작성한 checklist 가져오기(R)
	// 필요한 정보(아파트 코드, 아파트 이름, 아파트 위치)
	
	// 사용자가 작성한 checklist 세부 내용 가져오기(R)
	// 필요한 정보 (아파트 코드, 아파트 이름, 아파트 위치, 사용자가 입력한 값 정보)
	
	// 사용자 checklist 작성하기(C)
	// 필요한 값: 아파트 코드, 사용자 id, 사용자가 입력한 값
	@ResponseBody
	@PostMapping("/write")
	public ResponseEntity<?> writeChecklist(@RequestBody ChecklistWriteDto writeDto, HttpServletRequest request) throws ParseException{
		
		// 사용자의 id를 얻는다.
		String authorization=request.getHeader("Authorization");
		
		String[] chunks=authorization.split("\\.");
		Base64.Decoder decoder=Base64.getUrlDecoder();
		String payload=new String(decoder.decode(chunks[1]));
		
		JSONParser parser=new JSONParser();
		JSONObject jsonObject=(JSONObject) parser.parse(payload);
		String userId=(String) jsonObject.get("userId");
		
		// user id 값을 넣는다.
		writeDto.setUserId(userId);
		
		int upload=checklistService.writeChecklist(writeDto);
		
		if (upload!=0) {
			return new ResponseEntity(HttpStatus.CREATED);
		}
		return new ResponseEntity(HttpStatus.NOT_ACCEPTABLE);
	}
	// 사용자 checklist 수정하기(U)
	
	// 사용자 checklist 삭제하기(D)
}
