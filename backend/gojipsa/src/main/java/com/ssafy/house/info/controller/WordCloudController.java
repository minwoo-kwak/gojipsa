package com.ssafy.house.info.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.house.info.model.service.WordCloudService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/api/v1/wordcloud")
@Api(tags= {"워드클라우드 컨트롤러 API V1"})
public class WordCloudController {
	private final WordCloudService wordCloudService;

	public WordCloudController(WordCloudService wordCloudService) {
		this.wordCloudService = wordCloudService;
	}
	
	/**
	 * 부동산 시장 보고서를 파싱한 단어들을 가져옵니다.
	 * @return 단어 리스트, 상태값
	 */
	@GetMapping("words")
	@ApiOperation(value = "워드클라우드 단어리스트 가져오기", notes = "워드클라우드에 사용할 보고서 파싱 단어들을 가져옵니다.")
	public ResponseEntity<List<Map<String,Object>>> getWords() {
		List<Map<String,Object>> nounList = null;
		try {
			nounList =  wordCloudService.doWordAnalysis();
			return new ResponseEntity<List<Map<String,Object>>>(nounList, HttpStatus.CREATED);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<List<Map<String,Object>>>(nounList, HttpStatus.NO_CONTENT);
		}
	}
	
}
