package com.example.secondproject.domain.user;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class MemberDto {
    private Long id;
    private String loginid;
    private String password;
    private String name;
    private String email;

    public Member toEntity(){
        return Member.builder()
                .id(id)
                .loginid(loginid)
                .name(name)
                .password(password)
                .email(email)
                .build();

    }

    @Builder
    public MemberDto(Long id, String loginid, String name, String password, String email) {
        this.id = id;
        this.loginid = loginid;
        this.name = name;
        this.password = password;
        this.email = email;
    }

}
