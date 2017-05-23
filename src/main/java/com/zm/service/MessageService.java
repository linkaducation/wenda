package com.zm.service;

import com.zm.dao.MessageDAO;
import com.zm.model.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sun.plugin2.message.Conversation;

import java.util.List;

/**
 * Created by Ellen on 2017/5/22.
 */
@Service
public class MessageService {
    @Autowired
    private MessageDAO messageDAO;

    @Autowired
    private SensitiveService sensitiveService;

    public int addMessage(Message message) {
        int i = messageDAO.addMessage(message);
        return i > 0 ? 1 : 0;
    }

    public List<Message> getMessagetByFromId(int fromId) {
        return messageDAO.getMessagetByFromId(fromId);
    }

    public List<Message> getConversationList(int userId, int offset, int limit) {
        return messageDAO.getConversationList(userId, offset, limit);
    }

    public int getConversationUnreadCount(int userId, String counversationId) {
        return messageDAO.getConversationUnreadCount(userId, counversationId);
    }

    public List<Message> getConversationDetail(String conversation,int offset,int limit) {
        messageDAO.updateCoversationStatus(conversation);
        return messageDAO.getConversationDetail(conversation,offset,limit);
    }

}
