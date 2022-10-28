package com.example.secondproject.repository;

import com.example.secondproject.domain.board.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface BoardRepository extends JpaRepository<Board, Long>, BoardRepositoryCustom {


    @Query("select b from Board b left join fetch b.comments where b.id = :id")
    Board findBoardWithCommentByBoardId(@Param("id") Long id);

    @Query("select b from Board b left join fetch b.member where b.id = :id")
    Board findBoardWithMemberByBoardId(@Param("id") Long id);

    @Query("select b from Board b left join fetch b.member where b.member.id = :id")
    List<Board> findBoardsWithMemberByMemberId(@Param("id") Long memberId);


//    @Query("select b from Board b where b.nickname = :nickname")
//    Board findBynickname(@Param("nickname") String name);

//    @Query("select b from Board b left join fetch b.member where ")
//    Board findByBoardAndMemberByBoardId(Long boardId);
}
