package com.example.secondproject.controller;

import com.example.secondproject.domain.board.Board;
import com.example.secondproject.domain.user.Member;
import com.example.secondproject.dto.CommentReadDto;
import com.example.secondproject.dto.CommentRegisterDto;
import com.example.secondproject.dto.paging.BoardDto;
import com.example.secondproject.dto.BoardForm;
import com.example.secondproject.dto.paging.PageDto;
import com.example.secondproject.service.BoardService;
import com.example.secondproject.service.CommentService;
import com.example.secondproject.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import static com.example.secondproject.login.CustomUserDetailsService.hasAdminRole;

@Controller
@RequiredArgsConstructor
@Slf4j
public class BoardController {

    private final BoardService boardService;
    private final MemberService memberService;
    private final CommentService commentService;


    @GetMapping("/boards/new")
    public String createBoard(Model model) {
        log.info("BoardController getmapping createForm");


        //모델을 boards/writeboard.html로 넘김. html에서 ${boardForm}으로 사용 가능.
        model.addAttribute("boardForm", new BoardForm());

        return "/boards/writeBoard";
    }

    @PostMapping("/boards/new")
    public String createBoard(@Validated @ModelAttribute("boardForm") BoardForm form,
                              Principal principal) {

        log.info("BoardController postmapping createForm");

        Member findMember = memberService.findByEmail(principal.getName());

        Board board = new Board(form.getTitle(), form.getContent());

        boardService.save(board, findMember);

        return "redirect:/boards";
    }

    @GetMapping("/boards/{boardId}")//{boardId} : boardId를 바인딩
    public String readBoard(@PathVariable("boardId") Long id, Model model) {
        log.info("BoardController GetMapping readBoardForm");


        Board board = boardService.findBoardWithMemberByBoardId(id);//sql
        BoardDto boardDto = new BoardDto(id,board.getTitle(),
                board.getMember().getNickname(), board.getContent());//Dto로 view로 넘기기위해서.//sql

        List<CommentReadDto> commentList = new ArrayList<>();

        commentList = commentService.findCommentsWithMemberByBoardId(id);

        model.addAttribute("boardForm", boardDto);
        model.addAttribute("commentList", commentList);
        model.addAttribute("commentForm", new CommentRegisterDto());

        return "/boards/readBoard";
    }



    @GetMapping("/boards/{boardId}/edit")
    public String updateBoard(@PathVariable("boardId") Long boardId,
                                  Model model,
                                  Principal principal) {

        log.info("BoardController GetMapping updateBoardForm");

        Board one = boardService.findById(boardId);
        //admin이 아니고 작성자도 아니면.
        if (!one.getMember().getEmail().equals(principal.getName()) && !hasAdminRole()) {
            return "redirect:/boards/" + boardId;
        }

        BoardForm form = new BoardForm(one.getId(), one.getTitle(), one.getContent());//업데이트하는데 Board 엔티티를 안보내고 Board 폼을 보낼 것이다.


        model.addAttribute("boardForm", form);
        return "boards/updateBoardForm";
    }


    @PostMapping("/boards/{boardId}/edit")//뷰(readBoard.html)로부터 form이 넘어옴. 파라미터로 받음
    public String updateBoard(@PathVariable("boardId") Long boardId,
                             @ModelAttribute("boardForm") BoardForm boardForm) {

        //준영속 엔티티다.
        //getId해서 setId하였기에 한번 들어갔다 나왔기에 준영속 엔티티다.
        //왜냐하면 데이터베이스가 식별할 수 있는 Id를 가지고 있음.
        //JPA가 관리하지 않음. 그렇기에 변경 감지를 하지 않음.
        //준영속 엔티티를 수정하는 2가지 방법.
        //1. 변경 감지 기능 사용(더티체크)
        //2. 병합(merge) 사용

        log.info("BoardService PostMapping updateForm");

        boardService.update(boardId, boardForm.getTitle(), boardForm.getContent());

        return "redirect:/boards/" + boardId;

    }
//
////    th:href="@{/boards/form(id=${board.id})
//    http://localhost:8080/boards/form?id=1
//
//    action submit 버튼을 누르면 가지는 매핑정보.


    //폼은 get, post밖에 안되므로 <input type="hidden" name="_method" value="delete"/> 설정해야함.
    @DeleteMapping("/boards/{boardId}/delete")
    public String deleteBoard(@PathVariable("boardId") Long boardId,
                             Principal principal) {
        log.info("BoardController DeleteMapping deleteForm");
        Board one = boardService.findById(boardId);
        if (!one.getMember().getEmail().equals(principal.getName()) && !hasAdminRole()) {
            return "redirect:/boards/" + boardId;
        }

        boardService.deleteBoard(boardId);
        return "redirect:/boards";
    }


    /*
    Paging
    */
    @GetMapping("/boards")
    public String listBoard(Model model, @PageableDefault(size = 4, sort = "id",
            direction = Sort.Direction.DESC) Pageable pageable,
                       Principal principal) {
        log.info("BoardController getmapping list");

        Page<BoardDto> results = boardService.findPageSort(pageable);

        //모델을 boards/list.html로 넘김. html에서 ${boards}이름으로 사용 가능.
        model.addAttribute("boards", results.getContent());
        model.addAttribute("page", new PageDto(results.getTotalElements(), pageable));


//        return "boards/list";
        return "boards/pagingList";
    }

    //내 글
    @GetMapping("/my/boards")
    public String myBoards(Model model, Principal principal) {

        Member member = memberService.findByEmail(principal.getName());
        List<BoardDto> boards = boardService.findBoardsWithMemberByMemberId(member.getId());
        model.addAttribute("boards", boards);

        return "/users/myBoards";
    }
}
