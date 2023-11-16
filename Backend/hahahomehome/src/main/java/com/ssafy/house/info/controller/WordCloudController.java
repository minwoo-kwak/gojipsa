package com.ssafy.house.info.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.house.info.model.service.WordCloudService;

@RestController
@RequestMapping("/api/v1/wordcloud")
public class WordCloudController {
	private final WordCloudService wordCloudService;

	public WordCloudController(WordCloudService wordCloudService) {
		this.wordCloudService = wordCloudService;
	}
	
	@GetMapping("words")
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
