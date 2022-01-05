package com.example.secondproject.repository;


import com.example.secondproject.dto.paging.CommentDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CommentRepositoryCustom {
    Page<CommentDto> findAllPageSort(Pageable pageable, Long boardId);
}
