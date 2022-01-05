package com.example.secondproject.service;

import com.example.secondproject.domain.board.Comment;
import com.example.secondproject.dto.CommentReadDto;
import com.example.secondproject.dto.MyCommentDto;
import com.example.secondproject.dto.paging.CommentDto;
import com.example.secondproject.repository.BoardRepository;
import com.example.secondproject.repository.CommentRepository;
import com.example.secondproject.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CommentService {

    private final CommentRepository commentRepository;
    private final BoardRepository boardRepository;
    private final MemberRepository memberRepository;

    @Transactional(readOnly = true)
    public Page<CommentDto> findPageSort(Pageable pageable, Long boardId) {
        return commentRepository.findAllPageSort(pageable, boardId);
    }


    @Transactional
    public void save(Comment comment) {
        commentRepository.save(comment);
    }

    @Transactional(readOnly = true)
    public List<MyCommentDto> findCommentsWithBoardByEmail(String email) {
        List<Comment> comments = commentRepository.findCommentsWithBoardByEmail(email);
        List<MyCommentDto> myCommentDtos = new ArrayList<>();
        for (Comment comment : comments) {
            MyCommentDto myCommentDto = new MyCommentDto(comment.getId(), comment.getBoard().getTitle(),
                    comment.getBoard().getId(), comment.getContent());
            myCommentDtos.add(myCommentDto);
        }
        return myCommentDtos;
    }

    @Transactional(readOnly = true)
    public List<CommentReadDto> findCommentsWithMemberByBoardId(Long id) {
        List<Comment> comments = commentRepository.findCommentsWithMemberByBoardId(id);
        List<CommentReadDto> commentReadDtos = new ArrayList<>();

        for (Comment comment : comments) {
            CommentReadDto commentReadDto = new CommentReadDto(comment.getMember().getNickname(),
                    comment.getContent());
            commentReadDtos.add(commentReadDto);
        }
        return commentReadDtos;
    }
}
