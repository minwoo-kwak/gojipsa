package com.ssafy.house.board.model.dto;

import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BoardPaginationResponse {
	
	@ApiModelProperty(value="게시글 리스트")
	private List<BoardDto> content;
	
	@ApiModelProperty(value="페이지 번호")
	private int pageNo;
	
	@ApiModelProperty(value="페이지 크기")
	private int pageSize;
	
	@ApiModelProperty(value="게시글 총 개수")
	private long totalElements;
	
	@ApiModelProperty(value="페이지 총 개수")
	private int totalPages;
	
	
	private boolean last;
}
