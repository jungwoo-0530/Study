package com.example.secondproject.controller;

import com.example.secondproject.domain.user.User;
import com.example.secondproject.dto.JoinForm;
import com.example.secondproject.dto.LoginForm;
import com.example.secondproject.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@Slf4j
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
//회원가입
    @GetMapping("/join")
    public String joinForm(@ModelAttribute JoinForm joinForm) {
        return "/users/join";
    }

    @PostMapping("/join")
    public String join(@ModelAttribute @Validated JoinForm joinForm,
                       BindingResult bindingResult)
    {
        if (bindingResult.hasErrors()) {
            log.info(bindingResult.toString());
            return "/users/join";
        }

        User newUser = new User();
        newUser.setLoginid(joinForm.getLoginId());
        newUser.setName(joinForm.getName());
        newUser.setPassword(joinForm.getPassword());
        newUser.setEmail(joinForm.getEmail());

        userService.createUser(newUser);

        return "redirect:/";
    }

//로그인
    @GetMapping("/login")
    public String loginForm(@ModelAttribute LoginForm loginForm) {
        return "logins/loginForm";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute @Validated LoginForm loginForm,
                        BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            log.info(bindingResult.toString());
            return "logins/loginForm";
        }


        boolean checkLogin = userService.validationLogin(loginForm.getLoginid(), loginForm.getPassword());

        if (!checkLogin) {
            bindingResult.reject("loginFail", "아이디 또는 비밀번호가 맞지 않습니다.");
            return "logins/loginForm";
        }

        // 로그인 성공 처리

        return "redirect:/";
    }



}
