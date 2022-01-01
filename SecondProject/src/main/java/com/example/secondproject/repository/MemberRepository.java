package com.example.secondproject.repository;

import com.example.secondproject.domain.user.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

//메서드 이름으로 간단한 메서드를 구현하는 용도.
//querydsl을 사용할 것이면 querydsl을 사용하는 인터페이스도 extends.
@Repository
public interface MemberRepository extends JpaRepository<Member, Long>, MemberRepositoryCustom{

    Optional<Member> findByLoginid(String LoginId);

    Member findOneById(Long id);

}
