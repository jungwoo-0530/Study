package com.example.secondproject.service;

import com.example.secondproject.domain.user.Member;
import com.example.secondproject.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;


@Service
@Slf4j
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void createUser(Member member) {
        //password 암호화.
        String encodedPassword = passwordEncoder.encode(member.getPassword());
        member.setPassword(encodedPassword);
        memberRepository.save(member);
    }

    //password가 일치하지 않을 경우, null 반환.
    //findByLoginId의 반환이 optional이므로 null 반환 가능
    //https://youngjinmo.github.io/2021/05/passwordencoder/
//    public boolean validationLogin(String loginId, String password) {
//        Optional<Member> loginMember = memberRepository.findByLoginid(loginId);
//
//        if(!loginMember.isPresent()){
//            System.out.println("해당 아이디의 유저가 존재하지 않습니다");
//            return false;
//        }
//
//        if (!passwordEncoder.matches(password, loginMember.getPassword())) {
//            System.out.println("비밀번호가 일치하지 않습니다.");
//            return false;
//        }
//
//        return true;
//    }

    public List<Member> findAllMembers() {return memberRepository.findAll();}




    @Transactional
    @PostConstruct
    public void initUserDb() {
        Member member = new Member("admin", "김정우",
                "admin", "admin@naver.com");
//        user.setRoles(Arrays.asList(role));
        String encodedPassword = passwordEncoder.encode(member.getPassword());
        member.setPassword(encodedPassword);
        memberRepository.save(member);

        Member member1 = new Member("member", "김선우",
                "member", "member@naver.com");
//        user.setRoles(Arrays.asList(role));
        String encodedPassword1 = passwordEncoder.encode(member1.getPassword());
        member1.setPassword(encodedPassword1);
        memberRepository.save(member1);
    }



}
