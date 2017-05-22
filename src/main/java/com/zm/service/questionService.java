package com.zm.service;

import com.zm.dao.questionDao;
import com.zm.model.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.util.HtmlUtils;
import org.w3c.dom.html.HTMLUListElement;

import java.util.List;

/**
 * Created by Ellen on 2017/5/20.
 */
@Service
public class questionService {
    @Autowired
    private questionDao questionDao;
    @Autowired
    private SensitiveService ss;

    public int addQuestion() {
        return 0;
    }

    public List<Question> findLatestQuestion(int userId, int offset, int limit) {
        List<Question> list = questionDao.findLatestQuestion(userId, offset, limit);
        return list;
    }

    public int addQuestion(Question question) {
        //过滤标签
        question.setTitle(HtmlUtils.htmlEscape(question.getTitle()));
        question.setContent(HtmlUtils.htmlEscape(question.getContent()));
        question.setTitle(ss.filter(question.getTitle()));
        question.setContent(ss.filter(question.getContent()));
        question.setContent(question.getContent().replace("sa", "sa"));

        int result = questionDao.addQuestion(question);
        return result == 1 ? 1 : 0;
    }

    public Question findQuestionById(int id) {
        return questionDao.findQuestionById(id);
    }

    public void updateQuestion(Question question) {
        questionDao.updateQuestion(question);
    }
}
