package com.example.secondproject.repository.impl;

import com.example.secondproject.dto.paging.BoardDto;
import com.example.secondproject.dto.paging.QBoardDto;
import com.example.secondproject.repository.BoardRepositoryCustom;
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

import java.util.List;

import static com.example.secondproject.domain.board.QBoard.board;

@RequiredArgsConstructor
public class BoardRepositoryImpl implements BoardRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

//    @Override
//    public Page<BoardDto> findAllPageSort(Pageable pageable) {
//        return null;
//    }


    @Override
    public Page<BoardDto> findAllPageSort(Pageable pageable) {
        JPAQuery<BoardDto> query = jpaQueryFactory
                .select(new QBoardDto(
                        board.id.as("id"),
                        board.title.as("title"),
                        board.member.nickname.as("name")
                ))
                .from(board)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize());
        for (Sort.Order o : pageable.getSort()) {
            PathBuilder pathBuilder = new PathBuilder(board.getType(), board.getMetadata());
            query.orderBy(new OrderSpecifier(o.isAscending() ? Order.ASC : Order.DESC,
                    pathBuilder.get(o.getProperty())));
            System.out.println(o.getProperty());
        }
        System.out.println("board.getType() " + board.getType());
        System.out.println("board.getMetadata() "+board.getMetadata());
        QueryResults<BoardDto> results = query.fetchResults();
        List<BoardDto> content = results.getResults();
        long total = results.getTotal();

        return new PageImpl(content, pageable, total);

    }
}
