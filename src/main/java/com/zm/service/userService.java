package com.zm.service;

import com.zm.dao.userDao;
import com.zm.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Ellen on 2017/5/20.
 */
@Service
public class userService {
    @Autowired
    private userDao userDao;

    public User selectUserBuId(int userId) {
        User user = userDao.selectById(userId);
        return user;
    }

    public int updateUserPassword(User user) {
        int result = userDao.updatePassword(user);
        return result;
    }

    public int deleteUserBuId(int userId){
        int result = userDao.deleteById(userId);
        return result;
    }
}
