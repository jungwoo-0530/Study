package com.example.demo7springbootstartersecurity;

import com.example.demo7springbootstartersecurity.account.Account;
import com.example.demo7springbootstartersecurity.account.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class AccountRunner implements ApplicationRunner {
    @Autowired
    AccountService accountService;

    @Override
    public void run(ApplicationArguments args) throws Exception {

        Account jungwoo = accountService.createAccount("jungwoo", "1234");
        System.out.println(jungwoo.getUsername());
        System.out.println(jungwoo.getPassword());
    }
}
