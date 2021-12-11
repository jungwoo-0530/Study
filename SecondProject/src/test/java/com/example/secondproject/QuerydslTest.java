package com.example.secondproject;

import com.example.secondproject.domain.user.Member;
import com.example.secondproject.domain.user.QMember;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;


import javax.persistence.EntityManager;

import static com.example.secondproject.domain.user.QMember.member;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
public class QuerydslTest {

    @Autowired
    EntityManager em;

    @Test
    void test() {
        JPAQueryFactory queryFactory = new JPAQueryFactory(em);
        QMember qMember = new QMember("m");
        Member findMember = queryFactory.selectFrom(qMember).where(qMember.name.eq("김정우"))
                .fetchOne();

        assertThat(findMember.getName()).isEqualTo("김정우");
    }
}
