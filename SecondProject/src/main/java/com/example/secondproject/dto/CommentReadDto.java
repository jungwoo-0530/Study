package com.example.secondproject.dto;

import lombok.Data;

@Data
public class CommentReadDto {

    private String memberNickname;
    private String content;

    public CommentReadDto(String memberNickname, String content) {
        this.memberNickname = memberNickname;
        this.content = content;
    }

}
