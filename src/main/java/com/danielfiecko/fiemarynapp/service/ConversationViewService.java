package com.danielfiecko.fiemarynapp.service;

import com.danielfiecko.fiemarynapp.model.ConversationReply;
import com.danielfiecko.fiemarynapp.model.ConversationView;
import com.danielfiecko.fiemarynapp.model.User;

import java.util.List;

public interface ConversationViewService {

    ConversationView getConversationViewBetweenTwoUsers(int loggedUserId, int personToChatId);

    int searchConversationIdBetweenTwoUsers(int loggedUserId, int personToChatId);

    void sendMessage(ConversationReply someReply);

}
