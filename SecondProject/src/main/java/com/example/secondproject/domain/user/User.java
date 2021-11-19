package com.example.secondproject.domain.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "USER_ID")
    private Long id;

    private String loginid;

    private String name;

    private String password;

    private String email;

    public User(String loginid, String name, String password, String email) {
        this.loginid = loginid;
        this.name = name;
        this.password = password;
        this.email = email;
    }

    //주소는 api사용

}
