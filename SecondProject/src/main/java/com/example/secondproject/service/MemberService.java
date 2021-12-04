package com.example.secondproject.service;

import com.example.secondproject.domain.user.Member;
import com.example.secondproject.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.List;


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

    public List<Member> findAllMembers() {return memberRepository.findAll();}




    @Transactional
    @PostConstruct
    public void initUserDb() {
        Member member = new Member("admin", "김정우",
                "admin", "admin@naver.com", "ADMIN");
//        user.setRoles(Arrays.asList(role));
        String encodedPassword = passwordEncoder.encode(member.getPassword());
        member.setPassword(encodedPassword);
        memberRepository.save(member);

        Member member1 = new Member("member", "김선우",
                "member", "member@naver.com", "MEMBER");
//        user.setRoles(Arrays.asList(role));
        String encodedPassword1 = passwordEncoder.encode(member1.getPassword());
        member1.setPassword(encodedPassword1);
        memberRepository.save(member1);
    }



}
