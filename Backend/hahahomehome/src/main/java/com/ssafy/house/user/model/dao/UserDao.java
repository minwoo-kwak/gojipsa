package com.ssafy.house.user.model.dao;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.house.user.model.dto.User;

@Mapper
public interface UserDao {
	User selectByUser(User requestUser);
	
	User selectById(String userId);
}
