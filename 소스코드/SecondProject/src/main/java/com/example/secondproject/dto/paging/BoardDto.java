package com.example.secondproject.dto.paging;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class BoardDto {
    private Long id;
    private String title;
    private String nickname;
    private String content;

    @QueryProjection
    public BoardDto(Long id, String title, String nickname, String content) {
        this.id = id;
        this.title = title;
        this.nickname = nickname;
        this.content = content;
    }

}
