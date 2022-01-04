package com.example.secondproject.repository;

import com.example.secondproject.domain.board.Board;
import com.example.secondproject.domain.user.Member;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MemberRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    @Test
    @Transactional
    //fetch join을 사용하면 쿼리를 한번날리고 한번 날릴때, 연관관계인 boards(list)까지 다 가져옴.
    public void QueryJoinFetchTest() {

        String name = "김정우1";

//        Member findMember = memberRepository.findFatchByName(name);

        System.out.println("================test=============");
        for (Board board : findMember.getBoards()) {
            System.out.println(board.getId());
        }

    }

    @Test
    @Transactional
    //fetch join을 사용하지 않을 경우 쿼리를 두번 날려야함.
    public void NoQueryJoinFetchTest() {
        System.out.println("================test start==================");
        String name = "김정우1";

        Member findMember = memberRepository.findByName(name);

        System.out.println("================test=============");
        for (Board board : findMember.getBoards()) {
//            System.out.println(board.getId());
        }

    }

}