package com.example.secondproject.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import org.springframework.security.config.annotation.authentication.configuration.EnableGlobalAuthentication;

@Getter
@AllArgsConstructor
public class UserDetailDto {

    private Long id;
    private String title;
    private String nickname;

}
