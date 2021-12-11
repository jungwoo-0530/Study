package com.example.secondproject.repository;

import com.example.secondproject.domain.board.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long>, BoardRepositoryCustom {

    List<Board> findByTitle(String title);
}
