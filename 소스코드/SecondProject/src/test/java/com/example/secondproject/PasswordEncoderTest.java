package com.example.secondproject;

import com.example.secondproject.service.MemberService;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertAll;

@SpringBootTest
public class PasswordEncoderTest {

    @Autowired
    private MemberService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    @DisplayName("패스워드 암호화 테스트")
    void passwordEncode() {
        // given
        String rawPassword = "12345678";

        // when
        String encodedPassword = passwordEncoder.encode(rawPassword);

        // then
        assertAll(
                () -> assertNotEquals(rawPassword, encodedPassword),
                () -> assertTrue(passwordEncoder.matches(rawPassword, encodedPassword))
        );
    }
}
