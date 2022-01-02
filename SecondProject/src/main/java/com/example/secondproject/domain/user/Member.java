package com.example.secondproject.domain.user;

import com.example.secondproject.domain.board.Board;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
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

    private String email;

    private String password;

    private String nickname;

    private String name;

    private String role;

    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Board> boards = new ArrayList<>();


    public Member(String email, String password, String nickname, String name, String role) {

        this.nickname = nickname;
        this.name = name;
        this.password = password;
        this.email = email;
        this.role = role;
    }

    public void change(String nickname, String role) {
        this.setNickname(nickname);
        this.setRole(role);
    }


//    public void addBoard(Board board) {
//        this.boards.add(board);
//        board.setMember(this);
//    }






    //주소는 api사용

}
