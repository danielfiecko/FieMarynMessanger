package com.danielfiecko.fiemarynapp.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Entity
@Table(name = "users")
@EqualsAndHashCode()
@ToString()
public class User {

    public static final int minPasswordLength = 6;
    public static final int maxPasswordLength = 20;

    @Id
    @Getter
    @Setter
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private int id;

    @Getter
    @Setter
    @Size(min = 3, max = 50)
    @Column(name = "firstname", nullable = false)
    private String firstName;

    @Getter
    @Setter
    @Size(min = 3, max = 50)
    @Column(name = "secondname", nullable = false)
    private String secondName;

    @Getter
    @Setter
    @NotEmpty
    @Column(name = "nickname", unique = true, nullable = false)
    private String nickname;

    @Getter
    @Setter
    @Size(min = minPasswordLength, max = maxPasswordLength)
    @Column(name = "password", nullable = false)
    private String password;
}
