package com.example.secondproject.repository;

import com.example.secondproject.domain.board.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long>, CommentRepositoryCustom {

    @Query("select c from Comment c left join fetch c.board where c.member.email = :email")
    List<Comment> findCommentsWithBoardByEmail(@Param("email") String email);

    @Query("select c from Comment c left join fetch c.member where c.board.id = :id")
    List<Comment> findCommentsWithMemberByBoardId(@Param("id") Long boardId);

}
