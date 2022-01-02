package com.example.secondproject;

import com.example.secondproject.domain.board.Board;
import com.example.secondproject.domain.user.Member;
import com.example.secondproject.repository.BoardRepository;
import com.example.secondproject.repository.MemberRepository;
import com.example.secondproject.service.BoardService;
import com.example.secondproject.service.MemberService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class InitTest {

    @Autowired
    private MemberService memberService;

    @Autowired
    private BoardService boardService;

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private EntityManager em;

    @Test
    @Transactional
    void initTest() {
//        Member member = memberService.findOneById(2L);
        //28라인이 끝나고 준영속상태. 즉, 한번 영속상태가 되고 분리된 상태.
        //JPA가 이제 관리하지 않음.
        //그러기에 31라인이 오류남. member의 boards가 LAZY이기 때문에
        //could not initialize proxy - no Session에러가 나옴.

        Member member = memberRepository.findById(2L).get();

        //영속상태.
        System.out.println(member.getBoards().get(0).getName());
    }

    @Test
    @Transactional
    void initTest2() {
//        Board board = boardService.findById(2L);
//        System.out.println(board.getMember().getName());
//
//        System.out.println("11");

        Board board = boardRepository.findById(2L).get();
        System.out.println(board.getMember().getName());
    }

}