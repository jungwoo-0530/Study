package com.example.secondproject.domain.board;

import com.example.secondproject.domain.user.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "BOARD_ID")
    private Long id;

    private String title;

    private String name;//멤버의 name.

    //외래키.
    private String loginId;

    @Lob
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MEMBER_ID")//MEMBER_ID : FK
    private Member member;

    @OneToMany(mappedBy = "board", fetch = FetchType.LAZY)
    private List<Comment> comments = new ArrayList<>();

    public Board(String title, String name, String content, String loginId) {
        this.title = title;
        this.name = name;
        this.content = content;
        this.loginId = loginId;
    }

    //비지니스로직
    //객체지향 디자인 방법중에 GRASP.
    //정보를 가장 잘 알고 있는 곳에 로직(메서드)가 있어야 한다는 것.
    //Board가 해당 필드 정보를 가장 잘 알기 떄문에 여기에 비지니스 로직을 짠다.
    public void change(String title, String name, String content, String loginId) {
        this.setTitle(title);
        this.setName(name);
        this.setContent(content);
        this.setLoginId(loginId);
    }

    public void setMember(Member member) {
        this.member = member;
        member.getBoards().add(this);
    }

}
