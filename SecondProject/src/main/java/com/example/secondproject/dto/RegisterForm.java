package com.example.secondproject.dto;

import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class RegisterForm {

    @NotBlank(message = "id를 꼭 입력해 주세요")
    @Size(min = 5, max = 15, message = "id는 최소 5, 최대 15글자를 입력해주세요")
    private String loginId;

    @NotBlank
    private String password;

    @NotBlank
    private String name;

    @Email(message = "이메일 양식에 맞춰서 입력해주세요")
    @NotBlank(message = "이메일을 입력해주세요")//@Email이 null도 허용
    private String email;


    //@Pattern(regexp = "(01[016789])(\\d{3,4})(\\d{4})", message = "올바른 휴대폰 번호를 입력해주세요.")
    //핸드폰 번호

}
