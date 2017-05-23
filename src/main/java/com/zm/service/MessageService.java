package com.zm.service;

import com.zm.dao.MessageDAO;
import com.zm.model.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

}
