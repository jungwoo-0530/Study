package com.example.secondproject.controller;

import com.example.secondproject.domain.board.Comment;
import com.example.secondproject.domain.user.Member;
import com.example.secondproject.dto.CommentRegisterDto;
import com.example.secondproject.dto.MyCommentDto;
import com.example.secondproject.service.BoardService;
import com.example.secondproject.service.CommentService;
import com.example.secondproject.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;
import java.util.List;

@Controller
@Slf4j
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;
    private final MemberService memberService;
    private final BoardService boardService;


    @PostMapping("/boards/{boardId}")
    public String createComment(@ModelAttribute(name = "commentForm") @Validated CommentRegisterDto commentRegisterDto,
                                @PathVariable("boardId") Long boardId,
                                BindingResult bindingResult,
                                Principal principal) {

        Member sessionMember = memberService.findByEmail(principal.getName());//sql

        Comment comment = new Comment();
        comment.createComment(commentRegisterDto.getContent(), sessionMember,
                boardService.findById(boardId));//sql

        commentService.save(comment);
        return "redirect:/boards/" + boardId;
    }

    @GetMapping("/my/comments")
    public String myComments(Model model, Principal principal) {

        List<MyCommentDto> comments = commentService.findCommentsWithBoardByEmail(principal.getName());

        model.addAttribute("comments", comments);

        return "/users/myCommnets";
    }

}
