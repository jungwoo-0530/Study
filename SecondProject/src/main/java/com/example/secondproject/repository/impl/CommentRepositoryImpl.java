package com.example.secondproject.repository.impl;

import com.example.secondproject.repository.CommentRepositoryCustom;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CommentRepositoryImpl implements CommentRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;


}
