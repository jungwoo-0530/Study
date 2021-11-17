package com.example.secondproject.domain.board;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_id")
    private Long id;

    private String title;

    private String writer;

    @Lob
    private String content;


    //비지니스로직
    //객체지향 디자인 방법중에 GRASP.
    //정보를 가장 잘 알고 있는 곳에 로직(메서드)가 있어야 한다는 것.
    //Board가 해당 필드 정보를 가장 잘 알기 떄문에 여기에 비지니스 로직을 짠다.
    public void change(String title, String writer, String content) {
        this.setTitle(title);
        this.setWriter(writer);
        this.setContent(content);
    }

}