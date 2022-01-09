package com.example.secondproject.dto;

import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class RegisterForm {

    @NotBlank(message = "닉네임을 입력해 주세요")
    @Size(min = 5, max = 15, message = "닉네임은 최소 {min}, 최대 {max}글자를 입력해주세요")
    private String nickname;

    @NotBlank(message = "패스워드를 입력하세요")
    private String password;

    @NotBlank(message = "이름을 입력하세요")
    @Size(min = 1, max = 10, message = "이름은 최소 {min}, 최대 {max}글자를 입력해주세요")
    private String name;

    @Email(message = "이메일 양식에 맞춰서 입력해주세요")
    @NotBlank(message = "이메일을 입력해주세요")//@Email이 null도 허용
    private String email;


    //@Pattern(regexp = "(01[016789])(\\d{3,4})(\\d{4})", message = "올바른 휴대폰 번호를 입력해주세요.")
    //핸드폰 번호

}
