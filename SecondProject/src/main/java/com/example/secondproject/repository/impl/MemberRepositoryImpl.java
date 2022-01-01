package com.example.secondproject.repository.impl;

import com.example.secondproject.domain.user.Member;
import com.example.secondproject.domain.user.QMember;
import com.example.secondproject.dto.paging.MemberDto;
import com.example.secondproject.dto.paging.QMemberDto;
import com.example.secondproject.repository.MemberRepositoryCustom;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.example.secondproject.domain.user.QMember.member;


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

    @Override
    public Page<MemberDto> findAllPageSort(Pageable pageable) {
        JPAQuery<MemberDto> query = queryFactory
                .select(new QMemberDto(
                        qMember.id.as("id"),
                        qMember.name.as("name"),
                        qMember.loginId.as("loginId"),
                        qMember.email.as("email"),
                        qMember.role.as("role")
                ))
                .from(qMember)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize());
        for (Sort.Order o : pageable.getSort()) {
            PathBuilder pathBuilder = new PathBuilder(member.getType(), member.getMetadata());
            query.orderBy(new OrderSpecifier(o.isAscending() ? Order.ASC : Order.DESC,
                    pathBuilder.get(o.getProperty())));
            System.out.println(o.getProperty());
        }
        System.out.println("board.getType() " + member.getType());
        System.out.println("board.getMetadata() "+member.getMetadata());
        QueryResults<MemberDto> results = query.fetchResults();
        List<MemberDto> content = results.getResults();
        long total = results.getTotal();

        return new PageImpl(content, pageable, total);

    }
}
