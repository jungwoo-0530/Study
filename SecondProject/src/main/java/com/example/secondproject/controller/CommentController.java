package com.example.secondproject.controller;

import com.example.secondproject.domain.board.Comment;
import com.example.secondproject.domain.user.Member;
import com.example.secondproject.dto.CommentRegisterDto;
import com.example.secondproject.service.CommentService;
import com.example.secondproject.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;

@Controller
@Slf4j
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;
    private final MemberService memberService;


    @PostMapping("/boards/{boardId}")
    public String createComment(@ModelAttribute(name = "commentForm") CommentRegisterDto commentRegisterDto,
                                @PathVariable("boardId") Long boardId,
                                Principal principal) {

        Member sessionMember = memberService.findByEmail(principal.getName());
        Comment comment = new Comment(commentRegisterDto.getContent());
        commentService.save(comment, boardId, sessionMember.getId());

        return "redirect:/boards";
    }

}
