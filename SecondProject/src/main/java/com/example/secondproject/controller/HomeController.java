package com.example.secondproject.controller;

import com.example.secondproject.domain.order.Category;
import com.example.secondproject.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final CategoryRepository categoryRepository;

    @GetMapping("/")
    public String home() {
        return "home";
    }

    @GetMapping("/items")
    public String Categories(Model model) {

        List<Category> categoryList = categoryRepository.findAll();

        model.addAttribute("categoryList", categoryList);

        return "/orders/item/itemCategories";
    }

}
