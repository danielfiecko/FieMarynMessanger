package com.danielfiecko.fiemarynapp.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by DanielFiecko on 21/12/16.
 */

@Entity
@Table(name = "conversation_reply")
@EqualsAndHashCode()
@ToString()
public class ConversationReply {

    @Id
    @Getter
    @Setter
    @Column(name = "cr_id")
    private int id;

    @Getter
    @Setter
    @Column(name = "reply")
    private String reply;

    @Getter
    @Setter
    @Column(name = "user_id_fk")
    private int messageWriterId;

    @Getter
    @Setter
    @Column(name = "time")
    private int time;

    @Getter
    @Setter
    @Column(name = "c_id_fk")
    private int conversationId;
}
