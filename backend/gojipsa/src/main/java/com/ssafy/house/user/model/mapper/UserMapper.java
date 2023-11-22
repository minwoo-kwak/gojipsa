package com.ssafy.house.user.model.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.house.user.model.dto.User;

@Mapper
public interface UserMapper {
	User selectByUser(User requestUser);
	
	User selectById(String userId);

	void insertUser(User newUser);

	void updateUser(User updatedUser);

	void deleteUser(String userId);
	
	User getPassword(String userId);
}
