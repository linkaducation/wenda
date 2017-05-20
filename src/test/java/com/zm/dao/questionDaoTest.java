package com.zm.dao;

import com.zm.WendaApplication;
import com.zm.model.Question;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import static org.junit.Assert.*;
/**
 * Created by Ellen on 2017/5/19.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = WendaApplication.class)
public class questionDaoTest {
    @Autowired
    private questionDao questionDao;

    @Test
    public void addUser() throws Exception {
        Random random = new Random();
        int count = 0;
        while (count < 5) {
        Question question = new Question();
        question.setTitle(String.format("Titile{%d}", random.nextInt(1000)));
        question.setUserId(count + 1);
        question.setContent(String.format("Content{%d}", random.nextInt(100)));
        question.setCommentCount(random.nextInt(50));
        question.setCreateDate(new Date());
        questionDao.addQuestion(question);
            count++;
        }
    }

    @Test
    public void testfindLatestQuestion(){
        List<Question> list = questionDao.findLatestQuestion (1, 0, 10);
        Iterator<Question> iterator = list.iterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }
    }

}