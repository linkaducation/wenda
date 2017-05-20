package com.zm.service;

import com.zm.dao.questionDao;
import com.zm.model.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Ellen on 2017/5/20.
 */
@Service
public class questionService {
    @Autowired
    private questionDao questionDao;

    public int addQuestion() {
        return 0;
    }

    public List<Question> findLatestQuestion(int userId, int offset, int limit) {
        List<Question> list = questionDao.findLatestQuestion(userId, offset, limit);
        return list;
    }
}
