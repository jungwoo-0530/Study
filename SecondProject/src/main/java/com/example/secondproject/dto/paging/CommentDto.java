package com.example.secondproject.dto.paging;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter @Setter
public class CommentDto {

    private Long id;
    private String content;

    @QueryProjection
    public CommentDto(Long id, String content) {
        this.id = id;
        this.content = content;
    }
}
