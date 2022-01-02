package com.example.secondproject;

import com.example.secondproject.domain.board.Board;
import com.example.secondproject.domain.user.Member;
import com.example.secondproject.repository.MemberRepository;
import com.example.secondproject.service.BoardService;
import com.example.secondproject.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;

@Component
@RequiredArgsConstructor
@Slf4j
public class Init {
    private final MemberService memberService;
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final BoardService boardService;


    @PostConstruct
    @Transactional
    public void initDB() {

        Member member = new Member("admin", "김정우",
                "admin", "admin@naver.com", "ADMIN");
        String encodedPassword = passwordEncoder.encode(member.getPassword());
        member.setPassword(encodedPassword);
        memberRepository.save(member);

        int count = 0;
        int cnt = 0;
        while (cnt != 40) {
            cnt++;
            Member member1 = new Member("member" + cnt, "김정우" + cnt,
                    "member" + cnt, "member" + cnt + "@naver.com", "MEMBER");
            memberService.createUser(member1);
            for (int i = 0; i < 2; i++) {
                count++;
                Board board = new Board("제목test"+count, "김정우"+cnt, "안녕하세요. "+ count + "번째 글입니다",
                        "member"+cnt);
                boardService.save(board, member1);
            }
        }

    }

}
