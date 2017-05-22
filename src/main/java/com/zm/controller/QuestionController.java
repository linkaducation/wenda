package com.zm.controller;

import com.zm.model.HostHolder;
import com.zm.model.Question;
import com.zm.model.User;
import com.zm.service.questionService;
import com.zm.service.userService;
import com.zm.utils.WendaUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

/**
 * Created by Ellen on 2017/5/21.
 */
@Controller
public class QuestionController {
    private static final Logger logger = LoggerFactory.getLogger(QuestionController.class);

    @Autowired
    private questionService questionService;
    @Autowired
    private HostHolder hostHolder;
    @Autowired
    private userService userService;

    @RequestMapping(path = {"/question/add"}, method = {RequestMethod.POST})
    @ResponseBody
    public String addQuestion(@RequestParam("title") String title,
                              @RequestParam("content") String content) {
        try {
            Question question1 = new Question();
            if (hostHolder.getUser() != null) {
                question1.setUserId(hostHolder.getUser().getId());
            } else {
                return WendaUtil.getJSONString(999);
            }
            question1.setCreateDate(new Date());
            question1.setCommentCount(0);
            question1.setContent(content);
            question1.setTitle(title);
            int i = questionService.addQuestion(question1);
            if (i > 0) {
                return WendaUtil.getJSONString(0);
            }
        } catch (Exception e) {
            logger.error("添加失败：" + e.getMessage());
            return WendaUtil.getJSONString(0);
        }
        return WendaUtil.getJSONString(1, "失败");
    }

    @RequestMapping(path = {"/question/{id}"})
    public String findQuestionById(Model model,
                                 @PathVariable("id") int id) {
        Question question = questionService.findQuestionById(id);
        User user = userService.selectUserBuId(question.getUserId());
        model.addAttribute("user",user);
        model.addAttribute("question",question);
        return "detail";
    }
}
