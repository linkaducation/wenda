package com.zm.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Ellen on 2017/5/19.
 */
@Controller
public class indexController {
    @RequestMapping(path = {"/index"})
    @ResponseBody
    public String index() {
        return "hello";
    }

    @RequestMapping(path = {"/profile/{userId}/{groupId}"}, method = {RequestMethod.GET})
    @ResponseBody
    public String frofile(@PathVariable("userId") int userId,
                          @PathVariable("groupId") String groupId,
                          @RequestParam(value = "key", required = false, defaultValue = "2") int key) {
        return String.valueOf(userId) + " " + groupId + " " + key;
    }

    @RequestMapping(path = {"/for"})
    public String colors(Model model) {
        List list = Arrays.asList("BLUE","GREEN","RED");
        model.addAttribute("color",list);
        return "index";
    }
}
