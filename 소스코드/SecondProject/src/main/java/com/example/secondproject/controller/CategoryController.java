package com.example.secondproject.controller;

import com.example.secondproject.dto.order.provider.CategoryCreateForm;
import com.example.secondproject.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping("/admin/category/add")
    public String categoryAdd(Model model) {

        model.addAttribute("categoryForm", new CategoryCreateForm());


        return "/orders/admin/categoryAdd";
    }

    @PostMapping("/admin/category/add")
    public String categoryAdd(@ModelAttribute("categoryForm") CategoryCreateForm form) {

        System.out.println("===============================");
        System.out.println(form.getName());
        categoryService.saveByName(form.getName());

        return "redirect:/";
    }

    ///////////////////////////////////////////////////////////////////


}
