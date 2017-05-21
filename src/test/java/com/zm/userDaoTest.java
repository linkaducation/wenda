package com.zm;

import com.zm.dao.ticketLoginDAO;
import com.zm.dao.userDao;
import com.zm.model.LoginTicket;
import com.zm.model.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Random;

import static org.junit.Assert.*;

/**
 * Created by Ellen on 2017/5/19.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = WendaApplication.class)
public class userDaoTest {
    @Autowired
    private userDao dao;
    @Autowired
    private ticketLoginDAO ticketLoginDAO;
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Test
    public void addUser() throws Exception {
        Random random = new Random();
        for (int i = 0; i < 10; i++) {
            User user = new User();
            user.setName("钟明");
            user.setPassword("123456");
            user.setSalt("sasasa");
            user.setHeadUrl(String.format("http://images.nowcoder.com/head/%dt.png", random.nextInt(1000)));
            dao.addUser(user);
        }
    }

    @Test
    public void selectById() {
        User user = dao.selectById(1);
        User user1 = dao.selectById(2);
        logger.info("两个用户分别是：" + user + " " + user1);
    }

    @Test
    public void updatePassword() {
        User user = new User(1, "桃子", "123456", "salt", "somewhere");
        dao.updatePassword(user);
    }

    @Test
    public void ticket() {
        LoginTicket ticket = ticketLoginDAO.getTicket("zmc3a37");
        System.out.println(ticket);
    }

}