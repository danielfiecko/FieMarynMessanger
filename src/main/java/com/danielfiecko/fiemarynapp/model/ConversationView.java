package com.danielfiecko.fiemarynapp.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.List;

/**
 * Created by DanielFiecko on 21/12/16.
 */

@EqualsAndHashCode()
@ToString()
public class ConversationView {

    @Id
    @Getter
    @Setter
    private int userId;

    @Getter
    @Setter
    private String nickname;

    @Getter
    @Setter
    private int conversationId;

    @Getter
    @Setter
    private List<Integer> messageWroteBy;

    @Getter
    @Setter
    private List<String> replies;
}
