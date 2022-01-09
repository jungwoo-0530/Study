package com.example.secondproject.service;

import com.example.secondproject.domain.user.Member;
import com.example.secondproject.dto.paging.MemberDto;
import com.example.secondproject.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;
import java.util.Optional;


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


    @Transactional(readOnly = true)
    public Member findOneById(Long id) {
        return memberRepository.findOneById(id);
    }

    @Transactional
    public void deleteMember(Long memberId) {
        memberRepository.deleteById(memberId);
    }

    //orElse는 Optional에 값이 있든 말든 실행.
    //orElseThrow
    @Transactional(readOnly = true)
    public Member findOneByNickname(String nickname) {

        return memberRepository.findOneByNickname(nickname).orElseThrow(NoSuchElementException::new);

    }


    @Transactional
    public void updateByAdmin(Long id,String nickname, String role) {
        Member member = memberRepository.findOneById(id);

        member.change(nickname, role);
    }


    @Transactional(readOnly = true)
    public Page<MemberDto> findPageSort(Pageable pageable) {
        return memberRepository.findAllPageSort(pageable);
    }

    @Transactional(readOnly = true)
    public Member findMemberAndBoardsById(Long id) {
        return memberRepository.findMemberWithBoardsById(id);
    }


    public Member findByEmail(String name) {
        return memberRepository.findByEmail(name).orElseThrow(NoSuchElementException::new);
    }
}
