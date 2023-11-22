package com.ssafy.house.info.model.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import kr.co.shineware.nlp.komoran.model.Token;

public interface WordCloudService {
	public String parsePDF() throws IOException;
	
	public List<String> getNounList(String text);

	List<Map<String, Object>> doWordAnalysis() throws Exception;

	List<Map<String, Object>> doWordCount(List<String> pList) throws Exception;
}
