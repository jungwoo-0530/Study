package com.example.secondproject.service;

import com.example.secondproject.domain.board.Board;
import com.example.secondproject.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class BoardService {

    private final BoardRepository boardRepository;

    @Transactional
    public void save(Board board) {
        boardRepository.save(board);
    }

    @Transactional(readOnly = true)
    public List<Board> findAll() {
        return boardRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Board findById(Long id) {
        return boardRepository.findById(id).get();
    }

    @Transactional
    public void update(Long id, String title, String name, String content) {

        Board board = boardRepository.findById(id).get();//영속상태

        board.change(title, name, content);//변경감지, 비지니스로직을 엔티티에.

        //Board findOneBoard = boardRepository.findById(id).get();//영속상태.
    }

    @Transactional
    public void deleteBoard(Long id) {
        boardRepository.deleteById(id);
    }



    @PostConstruct
    @Transactional
    public void initBoardDb() {
        for (int i = 0; i < 100; i++) {
            Board board = new Board("test", "김정우", "안녕하세요. "+ i + "번째 글입니다");
            boardRepository.save(board);
        }
    }
}