package com.example.secondproject.repository.impl;

import com.example.secondproject.domain.user.Member;
import com.example.secondproject.domain.user.QMember;
import com.example.secondproject.repository.MemberRepositoryCustom;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

//MemberRepositoryCustom을 구현한 클래스.
@Repository
@RequiredArgsConstructor
public class MemberRepositoryImpl implements MemberRepositoryCustom {

    private final JPAQueryFactory queryFactory;

//    public MemberRepositoryImpl(JPAQueryFactory queryFactory) {
//        this.queryFactory = queryFactory;
//    }

    //QMember은 정적필드로 접근할 수 있음.
    QMember qMember = QMember.member;

    @Override
    public List<Member> getMemberList() {
        return queryFactory
                .selectFrom(qMember)
                .fetch();
    }
}
