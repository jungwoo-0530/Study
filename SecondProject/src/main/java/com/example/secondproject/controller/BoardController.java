package com.example.secondproject.controller;

import com.example.secondproject.domain.board.Board;
import com.example.secondproject.dto.BoardForm;
import com.example.secondproject.service.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class BoardController {

    private final BoardService boardService;

    @GetMapping("/boards")
    public String list(Model model){
        log.info("BoardController getmapping list");


        List<Board> boards = boardService.findAll();

        //모델을 boards/list.html로 넘김. html에서 ${boards}이름으로 사용 가능.
        model.addAttribute("boards", boards);

        return "boards/list";
    }


    @GetMapping("/boards/new")
    public String createForm(Model model) {
        log.info("BoardController getmapping createForm");

        //모델을 boards/writeboard.html로 넘김. html에서 ${boardForm}으로 사용 가능.
        model.addAttribute("boardForm", new BoardForm());
        return "/boards/writeBoard";
    }

    @PostMapping("/boards/new")
    public String createBoard(@Validated BoardForm form, BindingResult bindingResult) {

        log.info("BoardController postmapping createForm");
//        if (bindingResult.hasErrors()) {
//            return "board/writeboard";
//        }
//
        Board board = new Board();
        board.setName(form.getName());
        board.setTitle(form.getTitle());
        board.setContent(form.getContent());

        boardService.save(board);

        return "redirect:/boards";
    }

    @GetMapping("/boards/{boardId}")//{boardId} : boardId를 바인딩
    public String readBoardForm(@PathVariable("boardId") Long id, Model model) {
        log.info("BoardController GetMapping readBoardForm");

        Board board = boardService.findById(id);

        model.addAttribute("boardForm", board);
        return "/boards/readBoard";
    }

    @GetMapping("/boards/{boardId}/edit")
    public String updateBoardForm(@PathVariable("boardId") Long boardId, Model model) {
        log.info("BoardController GetMapping updateBoardForm");

        Board one = boardService.findById(boardId);
        BoardForm form = new BoardForm();//업데이트하는데 Board 엔티티를 안보내고 Board 폼을 보낼 것이다.

        form.setId(one.getId());
        form.setName(one.getName());
        form.setContent(one.getContent());
        form.setTitle(one.getTitle());

        model.addAttribute("boardForm", form);
        return "boards/updateBoardForm";
    }


    @PostMapping("/boards/{boardId}/edit")//뷰(readBoard.html)로부터 form이 넘어옴. 파라미터로 받음
    public String updateForm(@PathVariable("boardId") Long boardId, @ModelAttribute("boardForm") BoardForm boardForm)
    {

        //준영속 엔티티다.
        //getId해서 setId하였기에 한번 들어갔다 나왔기에 준영속 엔티티다.
        //왜냐하면 데이터베이스가 식별할 수 있는 Id를 가지고 있음.
        //JPA가 관리하지 않음. 그렇기에 변경 감지를 하지 않음.
        //준영속 엔티티를 수정하는 2가지 방법.
        //1. 변경 감지 기능 사용(더티체크)
        //2. 병합(merge) 사용
//        Board board = new Board();
//        board.setId(boardForm.getId());
//        board.setTitle(boardForm.getTitle());
//        board.setWriter(boardForm.getWriter());
//        board.setContent(boardForm.getContent());
//
//        boardService.updateBoard(boardForm.getId(), board);
        log.info("BoardService PostMapping updateForm");
        boardService.update(boardId, boardForm.getTitle(), boardForm.getName(), boardForm.getContent());

        return "redirect:/boards";

    }
//
////    th:href="@{/boards/form(id=${board.id})
//    http://localhost:8080/boards/form?id=1
//
//    action submit 버튼을 누르면 가지는 매핑정보.


    //폼은 get, post밖에 안되므로 <input type="hidden" name="_method" value="delete"/> 설정해야함.
    @DeleteMapping("/boards/{boardId}/delete")
    public String deleteForm(@PathVariable("boardId")Long boardId){
        log.info("BoardController DeleteMapping deleteForm");
        boardService.deleteBoard(boardId);
        return "redirect:/boards";
    }


}
