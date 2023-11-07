package com.ssafy.house.user.model.service;

import org.springframework.stereotype.Service;

import com.ssafy.house.user.model.dao.UserDao;
import com.ssafy.house.user.model.dto.User;

@Service
public class UserServiceImpl implements UserService{
	
	private final UserDao userDao;
	
	public UserServiceImpl(UserDao userDao) {
		this.userDao = userDao;
	}

	@Override
	public User login(User requestUser) {
		return userDao.selectByUser(requestUser);
	}

	@Override
	public User myPage(String userId) {
		return userDao.selectById(userId);
	}
	

}
