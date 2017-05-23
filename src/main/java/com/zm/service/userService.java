package com.zm.service;

import com.zm.dao.ticketLoginDAO;
import com.zm.dao.userDao;
import com.zm.model.LoginTicket;
import com.zm.model.User;
import com.zm.utils.WendaUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by Ellen on 2017/5/20.
 */
@Service
public class userService {
    @Autowired
    private userDao userDao;
    @Autowired
    private ticketLoginDAO ticketLogin;

    public User selectUserBuId(int userId) {
        User user = userDao.selectById(userId);
        return user;
    }

    public Map<String, Object> regist(String username, String password) {
        Map<String, Object> map = new HashMap<>();
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
        user = userDao.selectByName(user.getName());
        LoginTicket ticket = addTicket(user);
        map.put("ticket", ticket);
        return map;
    }

    public Map<String, Object> login(String username, String password) {
        Map<String, Object> map = new HashMap<>();
        if (StringUtils.isBlank(username)) {
            map.put("msg", "账号不能为空");
            return map;
        }
        if (StringUtils.isBlank(password)) {
            map.put("msg", "密码不能为空");
            return map;
        }
        User user = userDao.selectByName(username);
        if (user == null) {
            map.put("msg", "该帐号不存在");
            return map;
        }
        LoginTicket ticket1 = getTicket(WendaUtil.MD5(username + user.getSalt()));
        if (ticket1 == null) {
            LoginTicket ticket = addTicket(user);
            map.put("ticket", ticket);
        } else {
            ticket1.setStatus(0);
            Date now = new Date();
            now.setTime(now.getTime() + 1000 * 3600 * 24);
            ticket1.setExpired(now);
            updateTicket(ticket1);
            map.put("ticket", ticket1);
        }
        return map;
    }

    public void updateTicket(LoginTicket ticket) {
        ticketLogin.updateTicket(ticket);
    }

    public LoginTicket addTicket(User user) {
        LoginTicket ticket = new LoginTicket();
        ticket.setUserId(user.getId());
        Date now = new Date();
        now.setTime(now.getTime() + 1000 * 3600 * 24);
        ticket.setExpired(now);
        ticket.setStatus(0);
        ticket.setTicket(WendaUtil.MD5(user.getName() + user.getSalt()));
        ticketLogin.addTicket(ticket);
        return ticket;
    }

    public LoginTicket getTicket(String ticket) {
        LoginTicket reticket = ticketLogin.getTicket(ticket);
        return reticket;
    }

    public void logout(String t) {
        LoginTicket ticket = getTicket(t);
        ticket.setStatus(1);
        ticketLogin.updateTicket(ticket);
    }

    public User selectUserByName(String userName) {
        return userDao.selectByName(userName);
    }
}
