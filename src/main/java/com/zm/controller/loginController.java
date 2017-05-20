package com.zm.controller;

import com.zm.service.userService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

/**
 * Created by Ellen on 2017/5/20.
 */
@Controller
public class loginController {
    @Autowired
    userService userService;
    Logger logger = LoggerFactory.getLogger(this.getClass());

    @RequestMapping(path = {"/login"})
    public String login() {
        return "login";
    }

    @RequestMapping(path = {"/reg"}, method = RequestMethod.POST)
    public String regist(Model model,
                         @RequestParam("username") String username,
                         @RequestParam("password") String password) {
        try {
            Map<String, String> map = userService.regist(username, password);
            if (map.containsKey("msg")) {
                model.addAttribute("msg", map.get("msg"));
                return "/login";
            }
            return "redirect:/";
        } catch (Exception e) {
            logger.error(e.getMessage());
            return "/login";
        }
    }

}
