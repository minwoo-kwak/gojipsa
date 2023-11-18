package com.ssafy.house.checklist.controller;

import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.house.annotation.AuthRequired;
import com.ssafy.house.checklist.model.dto.ChecklistDetailDto;
import com.ssafy.house.checklist.model.dto.ChecklistSummaryDto;
import com.ssafy.house.checklist.model.dto.ChecklistUpdateDto;
import com.ssafy.house.checklist.model.dto.ChecklistWriteDto;
import com.ssafy.house.checklist.model.service.ChecklistService;
import com.ssafy.house.util.ApartPageConstant;
import com.ssafy.house.util.CheckListPageConstant;

@RestController
@RequestMapping("api/v1/checklist")
public class CheckListController {
	
	private final ChecklistService checklistService;

	public CheckListController(ChecklistService checklistService) {
		this.checklistService = checklistService;
	}
	

	/***
	 * 사용자가 작성한 체크리스트 가져오기 + pagination
	 * @param page: 페이지 번호
	 * @param request : 헤더에서 사용자의 id를 받아옴
	 * @return : 사용자가 작성한 체크리스트의 아파트 정보와 체크리스트 id / 상태코드
	 * @throws ParseException
	 */
	@ResponseBody
	@AuthRequired
	@GetMapping("/")
	public ResponseEntity<?> showAllChecklist(@RequestParam(value="page",required=false) String pageNo,HttpServletRequest request) throws ParseException{
		System.out.println("pageNo == " + pageNo);
		// 요청한 사용자의 id를 얻는다.
		String authorization=request.getHeader("Authorization");
		
		String[] chunks=authorization.split("\\.");
		Base64.Decoder decoder=Base64.getUrlDecoder();
		String payload=new String(decoder.decode(chunks[1]));
		
		JSONParser parser=new JSONParser();
		JSONObject jsonObject=(JSONObject) parser.parse(payload);
		String userId=(String)jsonObject.get("userId");
		
		// 응답으로 보낼 Map
		Map<String,Object> resultMap=new HashMap<>();
		
		// 체크리스트 목록을 가져올 때 필요한 정보를 map에 넣는다.
		Map<String,Object> map=new HashMap<>();
		map.put("userId", userId);
		int pgno=pageNo==null?1:Integer.parseInt(pageNo);
		int start=pgno*CheckListPageConstant.LIST_SIZE-CheckListPageConstant.LIST_SIZE;
		map.put("start", start);
		map.put("listSize",CheckListPageConstant.LIST_SIZE);
		
		// 페이지네이션을 적용하여 체크리스트 목록을 가져온다.
		List<ChecklistSummaryDto> data=checklistService.getAllChecklistById(map);
		resultMap.put("data", data);
		
		// 페이지 정보를 담을 HashMap
		HashMap<String,Object> pageInfo=new HashMap<>();
		long totalChecklistCnt=checklistService.countChecklist(userId);
		long totalPageCnt=(long)Math.ceil((totalChecklistCnt*1.0)/CheckListPageConstant.NAVIGATION_SIZE);
		pageInfo.put("totalPageCnt",totalPageCnt);
		
		// 페이지 그룹
		int pageGroup=(int)Math.ceil(pgno*1.0/CheckListPageConstant.NAVIGATION_SIZE);
		
		// 첫 페이지 번호
		long startPage=((pageGroup-1)*CheckListPageConstant.LIST_SIZE)+1;
		pageInfo.put("startPage",startPage);
		
		// 화면에 보여질 마지막 페이지 번호
		long lastPage=(pageGroup)*CheckListPageConstant.LIST_SIZE;
		if(lastPage>totalPageCnt) lastPage=totalPageCnt;
		pageInfo.put("lastPage",lastPage);
		
		resultMap.put("pageInfo",pageInfo);
		
		return new ResponseEntity<>(resultMap,HttpStatus.OK);
		
	}
	
	/**
	 * 사용자가 작성한 checklist 세부 내용 가져오기(R)
	 * @param chlistId : checklist id
	 * @param request : 헤더에서 사용자의 id를 받아옴
	 * @return : 사용자가 작성한 특정 checklist 내용
	 * @throws ParseException
	 */
	@ResponseBody
	@AuthRequired
	@GetMapping("/{chlistId}")
	public ResponseEntity<ChecklistDetailDto> showDetailChecklist(@PathVariable("chlistId") int chlistId,HttpServletRequest request) throws ParseException{
		
		// 요청한 사용자의 id를 얻는다.
		String authorization=request.getHeader("Authorization");
		
		String[] chunks=authorization.split("\\.");
		Base64.Decoder decoder=Base64.getUrlDecoder();
		String payload=new String(decoder.decode(chunks[1]));
		
		JSONParser parser=new JSONParser();
		JSONObject jsonObject=(JSONObject) parser.parse(payload);
		String userId=(String)jsonObject.get("userId");
		
		// 사용자의 id와 
		Map<String,Object> params=new HashMap<>();
		params.put("userId",userId);
		params.put("chlistId",chlistId);
		
		ChecklistDetailDto result=checklistService.getDetailChecklist(params);
		
		return new ResponseEntity<ChecklistDetailDto>(result, HttpStatus.OK);
		
	}

	/**
	 * 사용자 checklist 작성하기(C)
	 * @param writeDto : 새로 등록할 checklist 내용
	 * @param request : 헤더에서 받아올 사용자 id
	 * @return	: 성공(CREATED) 실패(NOT_ACCPETABLE)
	 * @throws ParseException
	 */
	@ResponseBody
	@AuthRequired
	@PostMapping("/")
	public ResponseEntity<?> writeChecklist(@RequestBody ChecklistWriteDto writeDto, HttpServletRequest request) throws ParseException{
		
		 //사용자의 id를 얻는다.
		String authorization=request.getHeader("Authorization");
		
		String[] chunks=authorization.split("\\.");
		Base64.Decoder decoder=Base64.getUrlDecoder();
		String payload=new String(decoder.decode(chunks[1]));
		
		JSONParser parser=new JSONParser();
		JSONObject jsonObject=(JSONObject) parser.parse(payload);
		String userId=(String)jsonObject.get("userId");
		
		// user id 값을 넣는다.
		writeDto.setUserId(userId);
		
		int upload=checklistService.writeChecklist(writeDto);
		
		if (upload!=0) {
			return new ResponseEntity(HttpStatus.CREATED);
		}
		return new ResponseEntity(HttpStatus.NOT_ACCEPTABLE);
	}

	/**
	 * 사용자 checklist 수정하기(U)
	 * @param updateDto : 수정할 내용
	 * @param request	: 헤더에서 받아올 사용자 id
	 * @return			: 성공(ACCEPTED), 실패(NOT_MODIFIED)
	 * @throws ParseException
	 */
	@ResponseBody
	@AuthRequired
	@PutMapping("/")
	public ResponseEntity<?> updateChecklist(@RequestBody ChecklistUpdateDto updateDto, HttpServletRequest request) throws ParseException{
		
		// 사용자의 id를 얻는다.
		String authorization=request.getHeader("Authorization");
		
		String[] chunks=authorization.split("\\.");
		Base64.Decoder decoder=Base64.getUrlDecoder();
		String payload=new String(decoder.decode(chunks[1]));
		
		JSONParser parser=new JSONParser();
		JSONObject jsonObject=(JSONObject) parser.parse(payload);
		String userId=(String)jsonObject.get("userId");
		
		updateDto.setUserId(userId);
		
		int updated=checklistService.updateChecklist(updateDto);
		
		if (updated!=0) {
			return new ResponseEntity(HttpStatus.ACCEPTED);
		}
		return new ResponseEntity(HttpStatus.NOT_MODIFIED);
		
		
	}
	// 사용자 checklist 삭제하기(D)
	/**
	 * 
	 * @param chlistId 	: 삭제할 checklist id
	 * @param request  	: 헤더에서 받아올 사용자 id
	 * @return			: 성공(ACCEPTED), 실패(NOT_MODIFIED)
	 * @throws ParseException
	 */
	@ResponseBody
	@AuthRequired
	@DeleteMapping("/{chlistId}")
	public ResponseEntity<?> deleteChecklist(@PathVariable("chlistId") int chlistId,HttpServletRequest request) throws ParseException{
		// 사용자의 id를 얻는다.
		String authorization=request.getHeader("Authorization");
		
		String[] chunks=authorization.split("\\.");
		Base64.Decoder decoder=Base64.getUrlDecoder();
		String payload=new String(decoder.decode(chunks[1]));
		
		JSONParser parser=new JSONParser();
		JSONObject jsonObject=(JSONObject) parser.parse(payload);
		String userId=(String)jsonObject.get("userId");
		
		Map<String,Object> param=new HashMap<>();
		param.put("chlistId", chlistId);
		param.put("userId", userId);
		
		int deleted=checklistService.deleteChecklist(param);
		
		if (deleted!=0) {
			return new ResponseEntity(HttpStatus.ACCEPTED);
		}
		return new ResponseEntity(HttpStatus.NOT_MODIFIED);
	}
}
