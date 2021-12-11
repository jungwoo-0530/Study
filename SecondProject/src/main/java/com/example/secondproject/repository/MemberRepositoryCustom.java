package com.example.secondproject.repository;


import com.example.secondproject.domain.user.Member;

import java.util.List;

//QueryDsl을 통해 사용하기 위한 쿼리를 정의하는 interface
public interface MemberRepositoryCustom {

    List<Member> getMemberList();

}
