package com.danielfiecko.fiemarynapp.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.Size;

/**
 * Created by DanielFiecko on 21/12/16.
 */

@Entity
@Table(name = "conversation")
@EqualsAndHashCode()
@ToString()
public class Conversation {

    @Id
    @Getter
    @Setter
    @Column(name = "c_id")
    private int id;

    @Getter
    @Setter
    @Column(name = "user_one")
    private int userOne;

    @Getter
    @Setter
    @Column(name = "user_two")
    private int userTwo;

    @Getter
    @Setter
    @Column(name = "time")
    private int time;
}
