package com.example.secondproject.service;

import com.example.secondproject.domain.user.User;
import com.example.secondproject.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.Optional;


@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    @Transactional
    public User createUser(User user) {
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        userRepository.save(user);
        return user;
    }

    //password가 일치하지 않을 경우, null 반환.
    //findByLoginId의 반환이 optional이므로 null 반환 가능
    //https://youngjinmo.github.io/2021/05/passwordencoder/
    public boolean validationLogin(String loginId, String password) {
        User loginUser = userRepository.findByLoginid(loginId);

        if(loginUser == null){
            System.out.println("해당 아이디의 유저가 존재하지 않습니다");
            return false;
        }

        if (!passwordEncoder.matches(password, loginUser.getPassword())) {
            System.out.println("비밀번호가 일치하지 않습니다.");
            return false;
        }

        return true;
    }


    @Transactional
    @PostConstruct
    public void initDB() {
        User user = new User("test", "김정우",
                "test", "test@naver.com");
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        userRepository.save(user);
    }



}
