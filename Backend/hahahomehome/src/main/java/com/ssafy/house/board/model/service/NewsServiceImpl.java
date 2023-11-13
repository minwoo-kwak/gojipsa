package com.ssafy.house.board.model.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Service;

import com.ssafy.house.board.model.dto.NewsDto;

@Service
public class NewsServiceImpl implements NewsService {

	private static final String CLIENT_ID = "a3BcriZ4YkKH61jGDQEs"; // 애플리케이션 클라이언트 아이디
	private static final String CLIENT_SECRET = "MFXDrjdCNo"; // 애플리케이션 클라이언트 시크릿

	@Override
	public List<NewsDto> getNews() throws ParseException, UnsupportedEncodingException {
		List<NewsDto> newsList = null;

		String apiURL = String.format("https://openapi.naver.com/v1/search/news.json?query=%s&display=5&sort=date",
				URLEncoder.encode("부동산", "UTF-8"));

		Map<String, String> requestHeaders = new HashMap<>();
		requestHeaders.put("X-Naver-Client-Id", CLIENT_ID);
		requestHeaders.put("X-Naver-Client-Secret", CLIENT_SECRET);

		String responseBody = get(apiURL, requestHeaders);

		JSONParser parser = new JSONParser();
		JSONObject obj = (JSONObject) parser.parse(responseBody);
		JSONArray items = (JSONArray) obj.get("items");
		newsList = new ArrayList<NewsDto>();
		for (int idx = 0; idx < items.size(); idx++) {
			NewsDto newsDto = new NewsDto();
			JSONObject item = (JSONObject) items.get(idx);

			System.out.println(item);
			newsDto.setTitle((String) item.get("title"));
			newsDto.setLink((String) item.get("link"));
			System.out.println(item.get("pubDate"));
//			newsDto.setPubDate((Date) item.get("pubDate"));

			newsList.add(newsDto);
		}

		return newsList;
	}

	private static String get(String apiUrl, Map<String, String> requestHeaders) {
		HttpURLConnection con = connect(apiUrl);
		try {
			con.setRequestMethod("GET");
			for (Map.Entry<String, String> header : requestHeaders.entrySet()) {
				con.setRequestProperty(header.getKey(), header.getValue());
			}

			int responseCode = con.getResponseCode();
			if (responseCode == HttpURLConnection.HTTP_OK) { // 정상 호출
				return readBody(con.getInputStream());
			} else { // 오류 발생
				return readBody(con.getErrorStream());
			}
		} catch (IOException e) {
			throw new RuntimeException("API 요청과 응답 실패", e);
		} finally {
			con.disconnect();
		}
	}

	private static HttpURLConnection connect(String apiUrl) {
		try {
			URL url = new URL(apiUrl);
			return (HttpURLConnection) url.openConnection();
		} catch (MalformedURLException e) {
			throw new RuntimeException("API URL이 잘못되었습니다. : " + apiUrl, e);
		} catch (IOException e) {
			throw new RuntimeException("연결이 실패했습니다. : " + apiUrl, e);
		}
	}

	private static String readBody(InputStream body) {
		InputStreamReader streamReader = new InputStreamReader(body);

		try (BufferedReader lineReader = new BufferedReader(streamReader)) {
			StringBuilder responseBody = new StringBuilder();

			String line;
			while ((line = lineReader.readLine()) != null) {
				responseBody.append(line);
			}

			return responseBody.toString();
		} catch (IOException e) {
			throw new RuntimeException("API 응답을 읽는 데 실패했습니다.", e);
		}
	}

}
