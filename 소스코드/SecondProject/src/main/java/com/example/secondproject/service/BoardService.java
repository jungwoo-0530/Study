package com.example.secondproject.service;

import com.example.secondproject.domain.board.Board;
import com.example.secondproject.domain.user.Member;
import com.example.secondproject.dto.paging.BoardDto;
import com.example.secondproject.repository.BoardRepository;
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
public class BoardService {

    private final BoardRepository boardRepository;

    @Transactional
    public void save(Board board, Member member) {
        board.setMember(member);
        boardRepository.save(board);
    }

    @Transactional(readOnly = true)
    public Board findById(Long id) {
        return boardRepository.findById(id).get();
    }



    @Transactional
    public void update(Long id, String title, String content) {

        Board board = boardRepository.findById(id).get();//영속상태

        board.change(title,content);//변경감지, 비지니스로직을 엔티티에.

        //Board findOneBoard = boardRepository.findById(id).get();//영속상태.
    }

    @Transactional
    public void deleteBoard(Long id) {
        boardRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public Page<BoardDto> findPageSort(Pageable pageable) {
        return boardRepository.findAllPageSort(pageable);
    }


    public Board findBoardWithCommentByBoardId(Long id) {
        return boardRepository.findBoardWithCommentByBoardId(id);
    }

    @Transactional(readOnly = true)
    public Board findBoardWithMemberByBoardId(Long id) {
        return boardRepository.findBoardWithMemberByBoardId(id);
    }


    @Transactional(readOnly = true)
    public List<BoardDto> findBoardsWithMemberByMemberId(Long id) {
        List<Board> boards = boardRepository.findBoardsWithMemberByMemberId(id);
        List<BoardDto> boardDtos = new ArrayList<>();

//        boards.forEach(b -> new BoardDto(b.getId(), b.getTitle(),
//                b.getMember().getNickname(), b.getContent()));
        for (Board board : boards ) {
            BoardDto boardDto = new BoardDto(board.getId(), board.getTitle(),
                    board.getMember().getNickname(), board.getContent());
            boardDtos.add(boardDto);
        }

        return boardDtos;
    }
}