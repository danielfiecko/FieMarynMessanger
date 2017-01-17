package com.danielfiecko.fiemarynapp.service;

import com.danielfiecko.fiemarynapp.dao.ConversationDao;
import com.danielfiecko.fiemarynapp.dao.UserDao;
import com.danielfiecko.fiemarynapp.model.ConversationReply;
import com.danielfiecko.fiemarynapp.model.ConversationView;
import com.danielfiecko.fiemarynapp.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service("conversationViewService")
@Transactional
public class ConversationViewServiceImpl implements ConversationViewService {

    @Autowired
    private ConversationDao dao;

    @Override
    public ConversationView getConversationViewBetweenTwoUsers(int loggedUserId, int personToChatId) {
        return dao.getConversationViewBetweenTwoUsers(loggedUserId, personToChatId);
    }

    @Override
    public int searchConversationIdBetweenTwoUsers(int loggedUserId, int personToChatId) {
        int receivedConversationId = -1;
        while (receivedConversationId < 0) {
            receivedConversationId = dao.getNewOrEarlierCreatedConversationId(loggedUserId, personToChatId);
        }
        return receivedConversationId;
    }

    @Override
    public void sendMessage(ConversationReply someReply) {
        dao.sendMessage(someReply);
    }
}
