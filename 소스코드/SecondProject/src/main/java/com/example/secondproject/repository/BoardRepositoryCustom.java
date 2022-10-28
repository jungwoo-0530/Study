package com.example.secondproject.repository;

import com.example.secondproject.dto.paging.BoardDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface BoardRepositoryCustom {
    Page<BoardDto> findAllPageSort(Pageable pageable);

}
