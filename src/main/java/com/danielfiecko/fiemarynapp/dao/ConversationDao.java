package com.danielfiecko.fiemarynapp.dao;

import com.danielfiecko.fiemarynapp.model.ConversationReply;
import com.danielfiecko.fiemarynapp.model.ConversationView;

import java.util.List;

/**
 * Created by DanielFiecko on 21/12/16.
 */
public interface ConversationDao {

    ConversationView getConversationViewBetweenTwoUsers(int loggedUserId, int personToChatId);

    int getNewOrEarlierCreatedConversationId(int loggedUserId, int personToChatId);

    void sendMessage(ConversationReply conversationReply);
}
