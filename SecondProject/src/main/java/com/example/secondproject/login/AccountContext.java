package com.example.secondproject.login;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

public class AccountContext extends User {

    private final com.example.secondproject.domain.user.User user;

    public AccountContext(com.example.secondproject.domain.user.User user, Collection<? extends GrantedAuthority> authorities) {
        super(user.getLoginid(), user.getPassword(), authorities);
        this.user = user;
    }

    public com.example.secondproject.domain.user.User getUser() {
        return user;
    }

}
