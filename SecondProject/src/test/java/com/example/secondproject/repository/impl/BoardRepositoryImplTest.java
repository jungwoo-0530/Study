//package com.example.secondproject.repository.impl;
//
//import com.example.secondproject.domain.board.Board;
//import com.example.secondproject.repository.BoardRepository;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.data.domain.Sort;
//import org.springframework.transaction.annotation.Transactional;
//
//import javax.persistence.EntityManager;
//
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//@SpringBootTest
//@Transactional
//class BoardRepositoryImplTest {
//
//    @Autowired
//    EntityManager em;
//
//    @Autowired
//    BoardRepository boardRepository;
//
//    @Test
//    public void findAllPageSortTest() throws Exception {
//        Board board1 = new Board("안녕하세요1", "김정우", "test1");
//        Board board2 = new Board("안녕하세요2", "김선우", "test2");
//        Board board3 = new Board("안녕하세요3", "김정우", "test3");
//        Board board4 = new Board("안녕하세요4", "김선우", "test4");
//        Board board5 = new Board("안녕하세요5", "김정우", "test5");
//        Board board6 = new Board("안녕하세요6", "김선우", "test6");
//        Board board7 = new Board("안녕하세요7", "김정우", "test7");
//
//        List<Board> results = boardRepository.findAllPageSort(PageRequest.of(1, 2, Sort.Direction.DESC, "title"));
//
//        assertAll("page : 1, size : 2, sort : title&DESC",
//                () -> assertEquals(results.get(0).getId(), board2.getId()),
//                () -> assertEquals(results.get(1).getId(), board1.getId()));
//
//        for (Board board : results) {
//            System.out.println("Board = " + board.getTitle());
//        }
//    }
//
//}