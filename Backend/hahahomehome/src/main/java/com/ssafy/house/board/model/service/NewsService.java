package com.ssafy.house.board.model.service;

import java.io.UnsupportedEncodingException;
import java.util.List;

import org.json.simple.parser.ParseException;

import com.ssafy.house.board.model.dto.NewsDto;

public interface NewsService {
	public List<NewsDto>  getNews() throws ParseException, UnsupportedEncodingException;
}
