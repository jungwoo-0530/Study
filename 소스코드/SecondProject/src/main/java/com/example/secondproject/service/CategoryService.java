package com.example.secondproject.service;

import com.example.secondproject.domain.order.Category;
import com.example.secondproject.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    @Transactional
    public void saveByName(String name) {
        Category category = new Category();
        category.setName(name);
        categoryRepository.save(category);
    }

    @Transactional(readOnly = true)
    public List<Category> findAll() {

        return categoryRepository.findAll();
    }

    public List<String> findAllName() {
        return categoryRepository.findAllName();
    }

    @Transactional(readOnly = true)
    public Category findCategoryByName(String categoryName) {
        return categoryRepository.findOneByName(categoryName);
    }
}
