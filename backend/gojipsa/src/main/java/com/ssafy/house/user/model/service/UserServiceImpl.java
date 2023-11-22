package com.ssafy.house.user.model.service;

import org.springframework.stereotype.Service;

import com.ssafy.house.user.model.dto.User;
import com.ssafy.house.user.model.mapper.UserMapper;

@Service
public class UserServiceImpl implements UserService {

    private final UserMapper userDao;

    public UserServiceImpl(UserMapper userDao) {
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

    @Override
    public void signup(User newUser) {
        userDao.insertUser(newUser);
    }
    
    @Override
    public boolean checkIdDuplication(String userId) {
        User user = userDao.selectById(userId);
        return user != null;
    }


    @Override
    public void updateUserInfo(String userId, User updatedUser) {
        // TODO: 유효성 검사
        updatedUser.setUserId(userId); // 업데이트할 사용자의 ID 설정
        
        System.out.println("updatedUser == " + updatedUser);
        userDao.updateUser(updatedUser);
    }

    @Override
    public void deleteUser(String userId) {
        userDao.deleteUser(userId);
    }
    
    /**
     * 현재 비밀번호의 유효성을 확인하는 메서드
     *
     * @param userId           사용자 ID
     * @param currentPassword  현재 비밀번호
     * @return 유효 여부
     */
    @Override
    public boolean checkCurrentPassword(String userId, String currentPassword) {
        User user = userDao.getPassword(userId);
        return user != null && currentPassword.equals(user.getPassword());
    }
}
