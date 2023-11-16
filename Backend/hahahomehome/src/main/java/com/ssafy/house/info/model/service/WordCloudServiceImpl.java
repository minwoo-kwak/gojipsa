package com.ssafy.house.info.model.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.text.PDFTextStripperByArea;
import org.springframework.stereotype.Service;

import kr.co.shineware.nlp.komoran.constant.DEFAULT_MODEL;
import kr.co.shineware.nlp.komoran.core.Komoran;
import kr.co.shineware.nlp.komoran.model.KomoranResult;

@Service
public class WordCloudServiceImpl implements WordCloudService {

	@Override
	public String parsePDF() {
		String filePath = "C:/parsingtest.pdf";
		File file = new File(filePath);

		try (PDDocument document = PDDocument.load(file);) {

			PDFTextStripperByArea stripper = new PDFTextStripperByArea();
			stripper.setSortByPosition(true);
			PDFTextStripper Tstripper = new PDFTextStripper();
			Tstripper.setStartPage(8);
			Tstripper.setEndPage(73);

			String text = Tstripper.getText(document);

			// 출력
//			System.out.println(text);

			// 정규식을 사용하여 한글, 영문, 숫자 이외의 문자를 공백으로 대체하고, 앞뒤 공백을 제거합니다.
			String replace_text = text.replace("[^가-힣a-zA-Z0-9]", " ");
			String trim_text = replace_text.trim();

			return trim_text;
		} catch (IOException e) {
			System.out.println("에러");
			e.printStackTrace();
		}

		return null;
	}

	@Override
	public List<String> getNounList(String text) {

		Komoran komoran = new Komoran(DEFAULT_MODEL.FULL);
		String strToAnalyze = text;

		KomoranResult analyzeResultList = komoran.analyze(strToAnalyze);

		List<String> nounList = analyzeResultList.getNouns();
		return nounList;
	}

	@Override
	public List<Map<String, Object>> doWordCount(List<String> pList) throws Exception {

		if (pList == null) {
			pList = new ArrayList<String>();
		}

		Set<String> rSet = new HashSet<String>(pList);
		Iterator<String> it = rSet.iterator();
		List<Map<String, Object>> rList = new ArrayList<>();

		while (it.hasNext()) {
			Map<String, Object> rMap = new HashMap<>();
			String word = it.next();
			if(isKeyword(word)) {
				continue;
			}
			
			int frequency = Collections.frequency(pList, word);
			if(frequency > 12) {
				rMap.put("text", word);
				rMap.put("size", frequency);				
				rList.add(rMap);
			}
		}

		return rList;
	}
	
    public static boolean isKeyword(String word) {
        // 특정 키워드들
        Set<String> keywords = new HashSet<>(Arrays.asList("시장", "지수", "분기", "주택", "부동산", "국면", "전", "국", "주", "기", "소"));

        // 대소문자 구분 없이 비교
        return keywords.contains(word);
    }

	@Override
	public List<Map<String, Object>> doWordAnalysis() throws Exception {

		List<String> pList = this.getNounList(parsePDF());

		if (pList == null) {
			pList = new ArrayList<String>();
		}

		List<Map<String, Object>> rList = this.doWordCount(pList);

		if (rList == null) {
			rList = new ArrayList<>();
		}

		return rList;
	}

}
