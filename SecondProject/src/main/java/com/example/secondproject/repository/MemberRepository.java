package com.example.secondproject.repository;

import com.example.secondproject.domain.user.Member;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

//메서드 이름으로 간단한 메서드를 구현하는 용도.
//querydsl을 사용할 것이면 querydsl을 사용하는 인터페이스도 extends.
@Repository
public interface MemberRepository extends JpaRepository<Member, Long>, MemberRepositoryCustom{

    Optional<Member> findOneByNickname(String nickname);

    Member findOneById(Long id);

    @Query("select m from Member m left join fetch m.boards where m.id = :id")
    Member findMemberWithBoardsById(@Param("id") Long id);


    Optional<Member> findByEmail(String email);

    //@Query : 사용자 정의 쿼리(@Query, 쿼리메서드, Named Query
    //여기서 쿼리는 JPQL(엔티티 대상으로 하는 쿼리)
    //파라미터 바인딩을 해야함. 두가지 방법 위치 기반, 이름기반.
    //이름기반만 사용.
    //tip.lazy로 된 연관관계를 함께 가지고 오고 싶다면 fetch join -> N+1 문제가 생기지 않음
//    @Query("select m from Member m left join fetch m.boards where m.name = :name")
//    Member findFatchByName(@Param("name") String name);
//
//    @Query("select m from Member m where m.name = :name")
//    Member findByName(String name);
}
