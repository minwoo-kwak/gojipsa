package com.ssafy.house.user.model.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class User {
	private String userId;
	private String password;
	private String name;
	private String grad;
	
}
