package com.example.secondproject.dto;

import lombok.Data;
import lombok.Getter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
public class JoinForm {

    @NotBlank
    private String loginId;

    @NotBlank
    private String password;

    @NotBlank
    private String name;

    @Email
    @NotBlank//@Email이 null도 허용
    private String email;

}
