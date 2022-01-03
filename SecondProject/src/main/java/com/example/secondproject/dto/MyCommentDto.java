package com.example.secondproject.dto;

import lombok.Data;

@Data
public class MyCommentDto {
    private Long id;
    private String boardTitle;
    private Long boardId;
    private String content;

    public MyCommentDto(Long id, String boardTitle, Long boardId, String content) {
        this.id = id;
        this.boardTitle = boardTitle;
        this.boardId = boardId;
        this.content = content;
    }
}
