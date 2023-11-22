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

	void signup(User newUser);

	void updateUserInfo(String userId, User updatedUser);

	void deleteUser(String userId);

	   /**
     * 아이디 중복 체크
     *
     * @param userId 아이디
     * @return true: 중복, false: 중복 아님
     */
	boolean checkIdDuplication(String userId);

	boolean checkCurrentPassword(String userId, String currentPassword);
}
