package com.ssafy.house.info.model.service;

import java.io.UnsupportedEncodingException;
import java.util.List;

import org.json.simple.parser.ParseException;

import com.ssafy.house.info.model.dto.NewsDto;

public interface NewsService {
	public List<NewsDto>  getNews(String currentPage) throws ParseException, UnsupportedEncodingException;
}
