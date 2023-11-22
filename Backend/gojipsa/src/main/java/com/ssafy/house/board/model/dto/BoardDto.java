package com.ssafy.house.board.model.dto;

import java.sql.Timestamp;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

// 게시글(공지사항) detail 정보
@Getter
@Setter
@ToString
public class BoardDto {
	
	@ApiModelProperty(value="게시글 번호")
	int board_no;
	
	@ApiModelProperty(value="제목")
	String title;
	
	@ApiModelProperty(value="내용")
	String content;
	
	@ApiModelProperty(value="등록 시간")
	Timestamp register_time;
	
	@ApiModelProperty(value="조회 수")
	int hit;
	
	@ApiModelProperty(value="글쓴이 아이디")
	String user_id;
	
}
