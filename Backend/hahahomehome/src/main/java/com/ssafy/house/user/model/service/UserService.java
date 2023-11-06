package com.ssafy.house.user.model.service;

import com.ssafy.house.user.model.dto.User;

public interface UserService {

	/**
	 * 
	 * @param requestUser
	 * @return
	 */
	User login(User requestUser);
	
	User myPage(String userId);
}
