package com.example.secondproject.controller;

import com.example.secondproject.domain.user.Member;
import com.example.secondproject.dto.RegisterForm;
import com.example.secondproject.service.MemberService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
@Slf4j
@AllArgsConstructor
public class MemberController {

    private final MemberService memberService;
    //회원가입
    @GetMapping("/register")
    public String joinForm(@ModelAttribute RegisterForm joinForm) {
        return "/users/register";
    }

    @PostMapping("/register")
    public String join(@ModelAttribute @Validated RegisterForm joinForm,
                       BindingResult bindingResult)
    {
        if (bindingResult.hasErrors()) {
            log.info(bindingResult.toString());
            return "/users/register";
        }

        Member newMember = new Member();
        newMember.setLoginid(joinForm.getLoginId());
        newMember.setName(joinForm.getName());
        newMember.setPassword(joinForm.getPassword());
        newMember.setEmail(joinForm.getEmail());

        memberService.createUser(newMember);

        return "redirect:/";
    }

//로그인
    /*
    @GetMapping("/login")
    public String loginForm(@ModelAttribute LoginForm loginForm) {
        return "users/loginForm";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute @Validated LoginForm loginForm,
                        BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            log.info(bindingResult.toString());
            return "users/loginForm";
        }


        boolean checkLogin = userService.validationLogin(loginForm.getLoginid(), loginForm.getPassword());

        if (!checkLogin) {
            bindingResult.reject("loginFail", "아이디 또는 비밀번호가 맞지 않습니다.");
            return "users/loginForm";
        }

        // 로그인 성공 처리

        return "redirect:/";
    }*/

    @GetMapping("/login")
    public String memberLogin() {
        return "users/loginForm1";
    }

    @GetMapping("/login/denied")
    public String memberLoginDenied(){
        return "/users/denied";
    }

    @GetMapping("/logout")
    public String memberLogout(HttpServletRequest request, HttpServletResponse response) {
        new SecurityContextLogoutHandler().logout(request, response, SecurityContextHolder.getContext().getAuthentication());
        return "redirect:/";
    }



    //유저 목록
    @GetMapping("/users")
    public String userList(Model model) {

        List<Member> members = memberService.findAllMembers();

        model.addAttribute("users", members);

        return "users/list";
    }

//    @GetMapping("/uesrs/{id}")
//    public String usersDetail(){
//
//    }



}
