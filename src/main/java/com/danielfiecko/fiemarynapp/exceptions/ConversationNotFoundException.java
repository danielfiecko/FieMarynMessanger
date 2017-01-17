package com.danielfiecko.fiemarynapp.exceptions;

/**
 * Created by danielfiecko on 15/01/17.
 */
public class ConversationNotFoundException extends Exception {
    public ConversationNotFoundException(int personToChatId){
        super("LoggedUser has not created conversation with this person [ contactId="+personToChatId+" ].");
    }
}
