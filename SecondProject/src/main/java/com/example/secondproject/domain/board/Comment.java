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

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "BOARD_ID")
//    private Board board;

    public void change(String name, String content) {
        this.setName(name);
        this.setContent(content);
    }

}
