package com.ssafy.house.user.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class User {
	
	@ApiModelProperty(value="사용자 아이디")
	private String userId;
	
	@ApiModelProperty(value="비밀번호")
	private String password;
	
	@ApiModelProperty(value="이름")
	private String name;
	
	@ApiModelProperty(value="등급")
	private String grad;
	
}
