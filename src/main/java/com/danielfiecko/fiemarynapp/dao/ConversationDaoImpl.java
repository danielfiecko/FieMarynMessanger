package com.danielfiecko.fiemarynapp.dao;

import com.danielfiecko.fiemarynapp.exceptions.ConversationNotFoundException;
import com.danielfiecko.fiemarynapp.model.ConversationReply;
import com.danielfiecko.fiemarynapp.model.ConversationView;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import java.util.*;


/**
 * Created by DanielFiecko on 21/12/16.
 */

@Repository("messageDao")
public class ConversationDaoImpl extends AbstractDao<Integer, ConversationView> implements ConversationDao {

    @Override
    public ConversationView getConversationViewBetweenTwoUsers(int loggedUserId, int personToChatId) {
        Query query = getSession().createSQLQuery("SELECT U.user_id, U.nickname, C.c_id, R.user_id_fk AS message_wrote_by, R.reply \n" +
                "FROM users U,conversation C, conversation_reply R\n" +
                "WHERE (\n" +
                "CASE\n" +
                "WHEN C.user_one = :contactId\n" +
                "THEN C.user_two = U.user_id\n" +
                "WHEN C.user_two = :contactId\n" +
                "THEN C.user_one= U.user_id\n" +
                "END\n" +
                ")\n" +
                "AND \n" +
                "C.c_id=R.c_id_fk\n" +
                "AND\n" +
                "U.user_id=:loggedUserId\n" +
                "AND\n" +
                "(C.user_one=:contactId OR C.user_two =:contactId) order by C.c_id DESC\n");

        query.setInteger("contactId", personToChatId);
        query.setInteger("loggedUserId", loggedUserId);

        ConversationView conversationView = new ConversationView();
        List<String> messages = new ArrayList<String>();
        List<Integer> messegeWroteIDs = new ArrayList<Integer>();
        List<Object> result = (List<Object>) query.list();

        Iterator messageIterator = result.iterator();
        while (messageIterator.hasNext()) {
            Object[] obj = (Object[]) messageIterator.next();
            messages.add(String.valueOf(obj[4]));
            messegeWroteIDs.add(Integer.valueOf((Integer) obj[3]));

        }
        conversationView.setReplies(messages);
        conversationView.setMessageWroteBy(messegeWroteIDs);

        Iterator detailsIterator = result.iterator();
        if (detailsIterator.hasNext()) {
            Object[] obj = (Object[]) detailsIterator.next();
            conversationView.setUserId(Integer.valueOf((Integer) obj[0]));
            conversationView.setNickname(String.valueOf(obj[1]));
            conversationView.setConversationId(Integer.valueOf((Integer) obj[2]));
        }
        return conversationView;
    }

    @Override
    public int getNewOrEarlierCreatedConversationId(int loggedUserId, int personToChatId) {

        int conversationId = -1;

        try {
            List<Object> result = injectQueryToDataBase(loggedUserId, personToChatId);
            conversationId = getConversationIdFromSqlResults(result, conversationId, personToChatId);
        } catch (ConversationNotFoundException e) {
            createConversationId(loggedUserId, personToChatId);
            this.getNewOrEarlierCreatedConversationId(loggedUserId, personToChatId);//rekurencja
            e.printStackTrace();
        }

        return conversationId;
    }

    private List<Object> injectQueryToDataBase(int loggedUserId, int personToChatId) {
        Query returnConversationIdFromSql = getSession().createSQLQuery("SELECT U.user_id, C.c_id " +
                "FROM users U,conversation C \n" +
                "WHERE (\n" +
                "CASE\n" +
                "WHEN C.user_one = :contactId\n" +
                "THEN C.user_two = U.user_id\n" +
                "WHEN C.user_two = :contactId\n" +
                "THEN C.user_one= U.user_id\n" +
                "END\n" +
                ")\n" +
                "AND\n" +
                "U.user_id=:loggedUserId\n" +
                "AND\n" +
                "(C.user_one=:contactId OR C.user_two =:contactId)");

        returnConversationIdFromSql.setInteger("loggedUserId", loggedUserId);
        returnConversationIdFromSql.setInteger("contactId", personToChatId);

        return (List<Object>) returnConversationIdFromSql.list();
    }

    private int getConversationIdFromSqlResults(List<Object> result, int conversationId, int personToChatId) throws ConversationNotFoundException {
        Iterator iterator = result.iterator();
        if (iterator.hasNext()) {
            Object[] obj = (Object[]) iterator.next();
            System.out.println("conversationId :" + String.valueOf(obj[1]));
            conversationId = Integer.valueOf((Integer) obj[1]);
        }
        if (conversationId == -1) {
            throw new ConversationNotFoundException(personToChatId);
        }
        return conversationId;
    }

    public void createConversationId(int loggedUserId, int personToChatId) {
        Query injectNewRows = getSession().createSQLQuery("INSERT INTO conversation(user_one,user_two) VALUES (:userOne,:userTwo);");
        injectNewRows.setInteger("userOne", loggedUserId);
        injectNewRows.setInteger("userTwo", personToChatId);
        injectNewRows.executeUpdate();
    }

    @Override
    public void sendMessage(ConversationReply conversationReply) {
        Query query = getSession().createSQLQuery("INSERT INTO conversation_reply(reply,user_id_fk,c_id_fk) VALUES (:messageContent,:messageWriterId,:conversationId);");
        query.setString("messageContent", conversationReply.getReply());
        query.setInteger("messageWriterId", conversationReply.getMessageWriterId());
        query.setInteger("conversationId", conversationReply.getConversationId());
        query.executeUpdate();
    }
}
