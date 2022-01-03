package com.example.secondproject.service;

import com.example.secondproject.domain.board.Board;
import com.example.secondproject.domain.board.Comment;
import com.example.secondproject.repository.BoardRepository;
import com.example.secondproject.repository.CommentRepository;
import com.example.secondproject.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class CommentService {

    private final CommentRepository commentRepository;
    private final BoardRepository boardRepository;
    private final MemberRepository memberRepository;


    @Transactional
    public void save(Comment comment) {
        commentRepository.save(comment);
    }
}
