package com.example.secondproject.login;

import com.example.secondproject.domain.user.Member;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

public class MemberAccount extends User {
    private final Member member;
    public MemberAccount(Member member, Collection<? extends GrantedAuthority> authorities) {
        super(member.getLoginId(), member.getPassword(), authorities);
        this.member = member;
    }

    public Member getMember() {
        return member;
    }

}
