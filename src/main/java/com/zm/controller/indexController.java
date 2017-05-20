package com.zm.controller;


import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Created by Ellen on 2017/5/19.
 */
@Controller
public class indexController {

    @RequestMapping(path = {"/profile/{userId}/{groupId}"}, method = {RequestMethod.GET})
    @ResponseBody
    public String frofile(@PathVariable("userId") int userId,
                          @PathVariable("groupId") String groupId,
                          @RequestParam(value = "key", required = false, defaultValue = "2") int key) {
        return String.valueOf(userId) + " " + groupId + " " + key;
    }

    @RequestMapping(path = {"/request"}, method = {RequestMethod.GET})
    @ResponseBody
    public String request(Model model,
                          HttpServletRequest request,
                          HttpServletResponse response,
                          HttpSession session) {
        StringBuilder sb = new StringBuilder();
        sb.append(request.getMethod() + "<br>");
        sb.append(request.getRequestURI() + "<br>");
        sb.append(request.getQueryString());
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            sb.append(cookie.getName() + " " + cookie.getValue());
        }
        response.addCookie(new Cookie("name", "zm"));
        return sb.toString();
    }

    @RequestMapping(path = {"/redirect"})
    public String redirect() {
        return "redirect:/index";
    }

    @RequestMapping(path = {"/redirect2/{state}"}, method = {RequestMethod.GET})
    public RedirectView redirect2(HttpSession session,
                                  @PathVariable("state") int code) {
        RedirectView red = new RedirectView("/index",true);
        if (code == 301) {
            red.setStatusCode(HttpStatus.MOVED_PERMANENTLY);
        }
        return red;
    }
}
