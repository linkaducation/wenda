package com.zm.intercepter;


import com.zm.dao.ticketLoginDAO;
import com.zm.dao.userDao;
import com.zm.model.HostHolder;
import com.zm.model.LoginTicket;
import com.zm.model.User;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

/**
 * Created by Ellen on 2017/5/21.
 */
@Component
public class PassportInterceptor implements HandlerInterceptor {
    @Autowired
    private HostHolder hostHolder;
    @Autowired
    private ticketLoginDAO loginDAO;
    @Autowired
    private userDao userDao;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {
        Cookie[] cookies = request.getCookies();
        String t = null;
        if (request.getRequestURI().contains("reg")) {
            return true;
        }
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("ticket")) {
                t = cookie.getValue();
                break;
            }
        }
        if (!StringUtils.isBlank(t)) {
            LoginTicket ticket = loginDAO.getTicket(t);
            if (ticket == null || ticket.getExpired().before(new Date()) || ticket.getStatus() == 1) {
                return true;
            }
            User user = userDao.selectById(ticket.getUserId());
            hostHolder.setUser(user);
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
        if (hostHolder.getUser() != null) {
            modelAndView.addObject("user", hostHolder.getUser());
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
        hostHolder.clear();
    }
}