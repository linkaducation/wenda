package com.zm.controller;

import com.zm.model.HostHolder;
import com.zm.model.LoginTicket;
import com.zm.model.User;
import com.zm.service.userService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * Created by Ellen on 2017/5/20.
 */
@Controller
public class loginController {
    @Autowired
    userService userService;
    @Autowired
    HostHolder hostHolder;

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @RequestMapping(path = {"/loginreg"})
    public String loginreg(@RequestParam(value = "next",required = false) String next,
                           Model model) {
        if (next != null) {
            model.addAttribute("next",next);
        }
        return "login";
    }

    @RequestMapping(path = {"/reg"}, method = RequestMethod.POST)
    public String regist(Model model,
                         @RequestParam("username") String username,
                         @RequestParam("password") String password,
                         @RequestParam(value = "next",required = false) String next,
                         HttpServletResponse response) {
        try {
            Map<String, Object> map = userService.regist(username, password);
            if (map.containsKey("msg")) {
                model.addAttribute("msg", map.get("msg"));
                return "/login";
            } else if (map.containsKey("ticket")) {
                LoginTicket ticket = (LoginTicket) map.get("ticket");
                Cookie cookie = new Cookie("ticket", ticket.getTicket());
                cookie.setMaxAge(3600 * 24 * 1000);
                cookie.setPath("/");
                response.addCookie(cookie);
                if (hostHolder.getUser() == null) {
                    User user = userService.selectUserBuId(ticket.getUserId());
                    hostHolder.setUser(user);
                }
                if (next != null) {
                    return "redirect:" + next;
                }
                return "redirect:/";
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            return "/login";
        }
        return null;
    }


    @RequestMapping(path = {"/login"}, method = RequestMethod.POST)
    public String login(Model model,
                        @RequestParam("username") String username,
                        @RequestParam("password") String password,
                        @RequestParam(value = "next",required = false) String next,
                        HttpServletResponse response) {
        try {
            Map<String, Object> map = userService.login(username, password);
            if (map.containsKey("msg")) {
                model.addAttribute("msg", map.get("msg"));
                return "/login";
            } else if (map.containsKey("ticket")) {
                Cookie cookie = new Cookie("ticket", ((LoginTicket) map.get("ticket")).getTicket());
                cookie.setMaxAge(3600 * 24 * 1000);
                cookie.setPath("/");
                response.addCookie(cookie);
                if (next != null) {
                    return "redirect:" + next;
                }
                return "redirect:/";
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            return "/login";
        }
        return null;
    }

    @RequestMapping(path = {"/logout"}, method = RequestMethod.GET)
    public String logout(@CookieValue("ticket") String ticket) {
        userService.logout(ticket);
        return "redirect:/";
    }

}
