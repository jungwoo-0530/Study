//package com.example.secondproject.repository;
//
//import com.example.secondproject.domain.user.Member;
//import com.querydsl.core.types.Order;
//import com.querydsl.core.types.OrderSpecifier;
//import com.querydsl.core.types.dsl.PathBuilder;
//import com.querydsl.jpa.impl.JPAQuery;
//import com.querydsl.jpa.impl.JPAQueryFactory;
//import org.springframework.data.domain.Pageable;
//import org.springframework.data.domain.Sort;
//
//import java.util.List;
//
//public class MemberRepositoryImpl implements MemberRepositoryCustom{
//
//    private final JPAQueryFactory queryFactory;
//
//    public MemberRepositoryImpl(JPAQueryFactory queryFactory) {
//        this.queryFactory = queryFactory;
//    }
//
//    @Override
//    public List<Member> findAllPageSort(Pageable pageable) {
//        JPAQuery<Member> query = queryFactory
//                .selectFrom(member)
//                .offset(pageable.getOffset())
//                .limit(pageable.getPageSize());
//        for (Sort.Order o : pageable.getSort()) {
//            PathBuilder pathBuilder = new PathBuilder(member.getType(), member.getMetadata());
//            query.orderBy(new OrderSpecifier(o.isAscending() ? Order.ASC : Order.DESC,
//                    pathBuilder.get(o.getProperty())));
//        }
//        List<Member> result = query.fetch();
//        return null;
//    }
//}
