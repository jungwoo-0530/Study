package com.example.secondproject.domain.board;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Setter @Getter
@NoArgsConstructor
@AllArgsConstructor
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BOARD_ID")//BOARD_ID FK
    private Board board;

    public Comment(String name, String content) {
        this.name = name;
        this.content = content;
    }

    public void change(String name, String content) {
        this.setName(name);
        this.setContent(content);
    }

    public void setBoard(Board board) {
        this.board = board;
        board.getComments().add(this);
    }

}
