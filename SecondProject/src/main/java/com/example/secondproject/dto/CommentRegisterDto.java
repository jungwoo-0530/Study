package com.example.secondproject.dto;

import lombok.Data;

import javax.persistence.Lob;
import javax.validation.constraints.NotBlank;

@Data
public class CommentRegisterDto {
    private Long id;

    private String content;
}
