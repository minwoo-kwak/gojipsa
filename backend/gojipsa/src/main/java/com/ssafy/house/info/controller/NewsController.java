package com.ssafy.house.info.controller;

import java.io.UnsupportedEncodingException;
import java.util.List;

import org.json.simple.parser.ParseException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.house.info.model.dto.NewsDto;
import com.ssafy.house.info.model.service.NewsService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/api/v1/news")
@Api(tags= {"뉴스 컨트롤러 API V1"})
public class NewsController {

	private final NewsService newsService;

	public NewsController(NewsService newsService) {
		this.newsService = newsService;
	}

	/**
	 * 네이버 뉴스 API로 받아온 최신 부동산 뉴스들을 가져옵니다.
	 * @param start 페이지 수와 페이지에 보여질 개수를 계산한 뉴스 시작 위치
	 * @return 해당 페이지에 해당하는 뉴스 리스트 
	 */
	@GetMapping("/{start}")
	@ApiOperation(value="뉴스 정보를 가져오기",notes="뉴스 정보를 보여주는 API")
	public ResponseEntity<List<NewsDto>> getNews(@PathVariable("start") String start) {
		List<NewsDto> newsList = null;
		try {
			newsList = newsService.getNews(start);
		} catch (UnsupportedEncodingException | ParseException e) {
			e.printStackTrace();
			return  new ResponseEntity<List<NewsDto>>(newsList, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return new ResponseEntity<List<NewsDto>>(newsList, HttpStatus.OK);

	}

}