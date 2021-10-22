package com.example.demo7mongodb;

import com.example.demo7mongodb.account.Account;
import com.example.demo7mongodb.account.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.MongoTemplate;

@SpringBootApplication
public class Demo7MongodbApplication {

    @Autowired
    private AccountRepository accountRepository;

    public static void main(String[] args) {

        SpringApplication.run(Demo7MongodbApplication.class, args);
    }


    @Bean
    public ApplicationRunner applicationRunner() {
        return args -> {
            Account account = new Account();
            account.setUsername("aa");
            account.setEmail("aaa@bbb");
            account.setUsername("jungwoo");

            accountRepository.insert(account);

            System.out.println("finished");
        };
    }

}
