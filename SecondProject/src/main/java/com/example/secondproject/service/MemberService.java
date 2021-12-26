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
        member.setRole("MEMBER");
        //password 암호화.
        String encodedPassword = passwordEncoder.encode(member.getPassword());
        member.setPassword(encodedPassword);
        memberRepository.save(member);
    }

    public List<Member> findAllMembers() {return memberRepository.findAll();}


    @Transactional(readOnly = true)
    public Member findOneById(Long id) {
        return memberRepository.findOneById(id);
    }

    @Transactional
    public void deleteMember(Long memberId) {
        memberRepository.deleteById(memberId);
    }

    @Transactional(readOnly = true)
    public Member findByLoginid(String loginId) {
        return memberRepository.findByLoginid(loginId).get();
    }

    @Transactional
    @PostConstruct
    public void initUserDb() {

        Member member = new Member("admin", "김정우",
                "admin", "admin@naver.com", "ADMIN");
        String encodedPassword = passwordEncoder.encode(member.getPassword());
        member.setPassword(encodedPassword);
        memberRepository.save(member);

        int cnt = 0;
        while (cnt != 40) {
            cnt++;
            Member member1 = new Member("member" + cnt, "김선우" + cnt,
                    "member" + cnt, "member" + cnt + "@naver.com", "MEMBER");
            String encodedPassword1 = passwordEncoder.encode(member1.getPassword());
            member1.setPassword(encodedPassword1);
            memberRepository.save(member1);
        }

    }


    @Transactional
    public void updateByAdmin(Long id, String name, String loginId, String email, String role) {
        Member member = memberRepository.findOneById(id);

        member.change(name, loginId, email, role);
    }
}
