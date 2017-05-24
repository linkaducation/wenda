package com.zm.service;

import com.zm.dao.CommentDAO;
import com.zm.model.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.util.HtmlUtils;

import java.util.List;

/**
 * Created by Ellen on 2017/5/22.
 */
@Service
public class CommentService {
    @Autowired
    private CommentDAO commentDAO;
    @Autowired
    private SensitiveService ss;

    public boolean addComment(Comment comment) {
        comment.setContent(HtmlUtils.htmlEscape(comment.getContent()));
        comment.setContent(ss.filter(comment.getContent()));
        int i = commentDAO.addComment(comment);
        return i > 0 ? true : false;
    }

    public List<Comment> getComments(int entityId, int entityType) {
        List<Comment> list = commentDAO.getCommentByEntity(entityId, entityType);
        return list;
    }

    public boolean deleteComment(Comment comment) {
        comment.setStatus(1);
        int i = commentDAO.updateComment(comment);
        return i > 0 ? true : false;
    }

    public int getCommentCount(int entityId, int entityType) {
        int count = commentDAO.getCommentCount(entityId, entityType);
        return count;
    }

    public Comment getConmmentById(int commentId) {
        return commentDAO.getCommentById(commentId);
    }
}
