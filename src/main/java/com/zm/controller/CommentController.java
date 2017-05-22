package com.zm.controller;

import com.zm.model.Comment;
import com.zm.model.EntityType;
import com.zm.model.HostHolder;
import com.zm.model.Question;
import com.zm.service.CommentService;
import com.zm.service.questionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;
import java.util.List;

/**
 * Created by Ellen on 2017/5/22.
 */
@Controller
public class CommentController {
    private static final Logger logger = LoggerFactory.getLogger(CommentController.class);
    @Autowired
    private CommentService commentService;
    @Autowired
    private HostHolder hostHolder;
    @Autowired
    private questionService questionService;

    @RequestMapping(path = {"/addComment"},method = {RequestMethod.POST})
    public String addComment (@RequestParam("content") String content,
                              @RequestParam("questionId") int questionId) {
        try {
            Comment comment = new Comment();
            if (hostHolder != null) {
                comment.setUserId(hostHolder.getUser().getId());
            } else {
                return "redirect:/loginreg";
            }
            comment.setStatus(0);
            comment.setContent(content);
            comment.setCreateDate(new Date());
            comment.setEntityType(EntityType.ENTITY_COMMENT);
            comment.setEntityId(questionId);
            commentService.addComment(comment);

            //更新评论数
            int count = commentService.getCommentCount(questionId, comment.getEntityType());
            Question question = questionService.findQuestionById(questionId);
            question.setCommentCount(count);
            questionService.updateQuestion(question);
        } catch (Exception e) {
            logger.error("添加回答失败： " + e.getMessage());
        }
        return "redirect:/question/" + String.valueOf(questionId);
    }


}
