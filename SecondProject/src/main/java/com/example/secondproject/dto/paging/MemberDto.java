package com.example.secondproject.dto.paging;


import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter @Setter
public class MemberDto {
    private Long id;
    private String name;
    private String loginid;
    private String email;
    private String role;

    @QueryProjection
    public MemberDto(Long id, String name, String loginid, String email, String role) {
        this.id = id;
        this.name = name;
        this.loginid = loginid;
        this.email = email;
        this.role = role;
    }

}
