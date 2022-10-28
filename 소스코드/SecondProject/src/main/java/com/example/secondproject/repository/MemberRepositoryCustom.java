package com.example.secondproject.repository;


import com.example.secondproject.domain.user.Member;
import com.example.secondproject.dto.paging.MemberDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

//QueryDsl을 통해 사용하기 위한 쿼리를 정의하는 interface
public interface MemberRepositoryCustom {

    Page<MemberDto> findAllPageSort(Pageable pageable);

}
