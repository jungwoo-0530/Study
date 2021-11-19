package com.example.secondproject.dto;

import lombok.Data;

import javax.persistence.Lob;
import javax.validation.constraints.NotBlank;

@Data
public class BoardForm {

    private Long id;

    @NotBlank(message = "작성자를 입력해주세요.")
    private String name;

    @NotBlank(message = "제목을 입력해주세요.")
    private String title;

    @NotBlank(message = "내용을 입력해주세요.")
    @Lob
    private String content;

}

