package com.ssafy.house.board.model.dto;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class NewsDto {
	/**
	 * {"originallink":"http:\/\/www.segye.com\/newsView\/20231113509646?OutUrl=naver",
	 * "link":"https:\/\/n.news.naver.com\/mnews\/article\/022\/0003874629?sid=102",
	 * "description":"A씨는 프랑스, 괌, 멕시코, 필리핀 등으로 매년 여러 차례 해외여행을 다녔고, 비트코인이나 <b>부동산<\/b> 투자에도 회삿돈을 썼다. 재판부는 &quot;피고인이 <b>부동산<\/b> 투자로 이익을 거뒀음에도 회사에 반환하지 못한 돈이 20억원이... ",
	 * "title":"30억 넘는 회삿돈 횡령\u2026경리 女직원 수법 보니",
	 * "pubDate":"Mon, 13 Nov 2023 23:42:00 +0900"}
	 */
	private int id;
	private String title;
	private String link;
	private String pubDate;
}
