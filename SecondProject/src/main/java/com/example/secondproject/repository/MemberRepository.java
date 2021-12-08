package com.example.secondproject.repository;

import com.example.secondproject.domain.user.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long>, MemberRepositoryCustom{

    Optional<Member> findByLoginid(String LoginId);

    Member findOneById(Long id);

    List<Member> findByName(String name);
}
