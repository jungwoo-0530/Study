package com.example.secondproject.repository;

import com.example.secondproject.domain.order.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {

    @Query("select i from Item i where i.member.email = :email")
    List<Item> findItemsByMemberEmail(@Param("email")String email);


    @Query("select i from Item i where i.category.id = :id")
    List<Item> findItemsByCategoryId(@Param("id") Long categoryId);
}
