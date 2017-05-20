package com.zm.service;

import com.zm.dao.userDao;
import com.zm.model.User;
import com.zm.utils.WendaUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

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

    public Map<String, String> regist(String username, String password) {
        Map<String, String> map = new HashMap<>();
        if (StringUtils.isBlank(username)) {
            map.put("msg", "账号不能为空");
            return map;
        }
        if (StringUtils.isBlank(password)) {
            map.put("msg", "密码不能为空");
            return map;
        }
        User user = userDao.selectByName(username);
        if (user != null) {
            map.put("msg", "该帐号已被注册");
            return map;
        }
        user = new User();
        user.setName(username);
        user.setSalt(UUID.randomUUID().toString().substring(0, 5));
        user.setPassword(WendaUtil.MD5(password));
        user.setHeadUrl(String.format("http://images.nowcoder.com/head/%dt.png", new Random().nextInt(1000)));
        userDao.addUser(user);
        return map;
    }

    public int updateUserPassword(User user) {
        int result = userDao.updatePassword(user);
        return result;
    }

    public int deleteUserBuId(int userId) {
        int result = userDao.deleteById(userId);
        return result;
    }
}
