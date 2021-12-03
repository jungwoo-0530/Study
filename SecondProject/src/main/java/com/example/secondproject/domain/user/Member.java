package com.example.secondproject.domain.user;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MEMBER_ID")
    private Long id;

    private String loginid;

    private String name;

    private String password;

    private String email;


    @Builder
    public Member(Long id, String loginid, String name, String password, String email) {
        this.id = id;
        this.loginid = loginid;
        this.name = name;
        this.password = password;
        this.email = email;
    }
    public Member(String loginid, String name, String password, String email) {

        this.loginid = loginid;
        this.name = name;
        this.password = password;
        this.email = email;
    }

    //주소는 api사용

}
