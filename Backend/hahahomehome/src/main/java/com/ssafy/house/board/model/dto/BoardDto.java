package com.ssafy.house.board.model.dto;

import java.sql.Timestamp;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

// 게시글(공지사항) detail 정보
@Getter
@Setter
@ToString
public class BoardDto {
	
	int board_no;
	String title;
	String content;
	Timestamp register_time;
	int hit;
	String user_id;
	
	
	
}
