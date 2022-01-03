package com.example.secondproject.repository.impl;

import com.example.secondproject.dto.paging.CommentDto;
import com.example.secondproject.dto.paging.QCommentDto;
import com.example.secondproject.repository.CommentRepositoryCustom;
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

import static com.example.secondproject.domain.board.QComment.comment;

@RequiredArgsConstructor
public class CommentRepositoryImpl implements CommentRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Page<CommentDto> findAllPageSort(Pageable pageable) {
        JPAQuery<CommentDto> query = jpaQueryFactory
                .select(new QCommentDto(
                        comment.id.as("id"),
                        comment.content.as("content")
                ))
                .from(comment)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize());
        for (Sort.Order o : pageable.getSort()) {
            PathBuilder pathBuilder = new PathBuilder(comment.getType(), comment.getMetadata());
            query.orderBy(new OrderSpecifier(o.isAscending() ? Order.ASC : Order.DESC,
                    pathBuilder.get(o.getProperty())));
//            System.out.println(o.getProperty());
        }
//        System.out.println("board.getType() " + comment.getType());
//        System.out.println("board.getMetadata() "+comment.getMetadata());
        QueryResults<CommentDto> results = query.fetchResults();
        List<CommentDto> content = results.getResults();
        long total = results.getTotal();

        return new PageImpl(content, pageable, total);
    }

}
