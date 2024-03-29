package com.bookstore.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@EqualsAndHashCode
@Table(name = "user")
public class UserDAO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Getter
    @Setter
    @Column
    private String username;

    @Getter
    @Setter
    @Column
    @JsonIgnore
    private String password;
}
