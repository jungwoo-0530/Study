package com.example.secondproject.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserDetailDto {

    private Long id;
    private String title;
    private String nickname;

}
