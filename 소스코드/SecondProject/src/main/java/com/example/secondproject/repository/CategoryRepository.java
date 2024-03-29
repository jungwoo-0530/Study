package com.example.secondproject.repository;

import com.example.secondproject.domain.order.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    @Query("select c.name from Category c")
    List<String> findAllName();

    Category findOneByName(String categoryName);
}
