package com.example.secondproject.domain.user;

import com.example.secondproject.domain.board.Board;
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

    private String loginId;

    private String name;

    private String password;

    private String email;

    private String role;




    public Member(String loginId, String name, String password, String email, String role) {

        this.loginId = loginId;
        this.name = name;
        this.password = password;
        this.email = email;
        this.role = role;
    }

    public void change(String name, String loginId, String email, String role) {
        this.setName(name);
        this.setLoginId(loginId);
        this.setEmail(email);
        this.setRole(role);
    }


    //주소는 api사용

}
