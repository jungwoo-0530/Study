package com.example.secondproject.domain.user;

import lombok.*;

import javax.persistence.*;

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

    private String role;


    public Member(String loginid, String name, String password, String email, String role) {

        this.loginid = loginid;
        this.name = name;
        this.password = password;
        this.email = email;
        this.role = role;
    }

    //주소는 api사용

}
