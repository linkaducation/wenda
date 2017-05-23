package com.zm.controller;

import com.zm.model.HostHolder;
import com.zm.model.Message;
import com.zm.model.User;
import com.zm.model.ViewObject;
import com.zm.service.MessageService;
import com.zm.service.SensitiveService;
import com.zm.service.userService;
import com.zm.utils.WendaUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.swing.text.View;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

    @RequestMapping(path = {"/msg/addMessage"}, method = {RequestMethod.POST})
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
            return WendaUtil.getJSONString(999, "添加站内信失败");
        }
    }

    @RequestMapping(path = {"/msg/list"}, method = {RequestMethod.GET})
    public String conversationDetail(Model model) {
        try {
            User user = hostHolder.getUser();
            List<Message> messagets = messageService.getMessagetByFromId(user.getId());
            List<ViewObject> vos = new ArrayList<ViewObject>();
            for (Message messaget : messagets) {
                ViewObject ov = new ViewObject();
                ov.set("conversation", messaget);
                int targetId = messaget.getFromId() == user.getId() ? messaget.getToId() : messaget.getFromId();
                User toUser = userService.selectUserBuId(targetId);
                ov.set("user", toUser);
                ov.set("unread", messageService.getConversationUnreadCount(user.getId(), messaget.getConversationId()));
                vos.add(ov);
            }
            model.addAttribute("conversations", vos);
        } catch (Exception e) {
            logger.error("查询出错： " + e.getMessage());
        }
        return "letter";
    }

    @RequestMapping(path = {"/msg/detail"}, method = RequestMethod.GET)
    public String getDetail(@RequestParam("conversationId") String conversationId,
                            Model model) {
        try {
            User user = hostHolder.getUser();
            if (user == null) {
                return "redirect:/loginreg";
            }
            List<Message> messages = messageService.getConversationDetail(conversationId,0,10);
            List<ViewObject> vos = new ArrayList<ViewObject>();
            for (Message message : messages) {
                ViewObject vo = new ViewObject();
                vo.set("message",message);
                User user1 = userService.selectUserBuId(message.getFromId());
                if (user == null) {
                    continue;
                }
                vo.set("headUrl",user1.getHeadUrl());
                vo.set("userId",user1.getId());
                vos.add(vo);
            }
            model.addAttribute("messages",vos);
        } catch (Exception e) {
            logger.error("查询失败：" + e.getMessage());
        }
        return "letterDetail";
    }

}

