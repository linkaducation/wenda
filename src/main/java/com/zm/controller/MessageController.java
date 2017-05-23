package com.zm.controller;

import com.zm.model.HostHolder;
import com.zm.model.Message;
import com.zm.model.User;
import com.zm.service.MessageService;
import com.zm.service.SensitiveService;
import com.zm.service.userService;
import com.zm.utils.WendaUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;

/**
 * Created by Ellen on 2017/5/22.
 */
@Controller
public class MessageController {
    private static final Logger logger = LoggerFactory.getLogger(MessageController.class);
    @Autowired
    private HostHolder hostHolder;
    @Autowired
    private MessageService messageService;
    @Autowired
    private SensitiveService ss;
    @Autowired
    private userService userService;

    @RequestMapping(path = {"/msg/addMessage"},method = {RequestMethod.POST})
    @ResponseBody
    public String addMessage(@RequestParam("toName") String toName,
                             @RequestParam("content") String content) {
        try {
            User user = hostHolder.getUser();
            if (user == null) {
                return "loginreg";
            }
            User toUser = userService.selectUserByName(toName);
            Message message = new Message();
            message.setHasRead(0);
            message.setCreatedDate(new Date());
            message.setContent(ss.filter(content));
            message.setFromId(user.getId());
            message.setToId(toUser.getId());
            int i = messageService.addMessage(message);
            if (i == 0) {
                throw new Exception();
            }
            return WendaUtil.getJSONString(0);
        } catch (Exception e) {
            logger.error("添加消息失败" + e.getMessage());
            return WendaUtil.getJSONString(999,"添加站内信失败");
        }
    }
}
