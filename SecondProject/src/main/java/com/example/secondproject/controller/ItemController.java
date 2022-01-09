package com.example.secondproject.controller;

import com.example.secondproject.domain.order.Category;
import com.example.secondproject.domain.user.Member;
import com.example.secondproject.dto.order.ItemCreateForm;
import com.example.secondproject.service.CategoryService;
import com.example.secondproject.service.ItemService;
import com.example.secondproject.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class ItemController {

    private final ItemService itemService;
    private final CategoryService categoryService;
    private final MemberService memberService;

    @GetMapping("/provider/item/add")
    public String createItem(Model model) {

        List<String> categories = categoryService.findAllName();

        model.addAttribute("itemForm", new ItemCreateForm());
        model.addAttribute("categoryNames", categories);

        return "/orders/itemCreate";
    }

    @PostMapping("/provider/item/add")
    public String createItem(@ModelAttribute("itemForm") ItemCreateForm form,
                             Principal principal) {

        Category category = categoryService.findCategoryByName(form.getCategoryName());
        Member member = memberService.findByEmail(principal.getName());
        itemService.save(form, category, member);

        return "redirect:/";
    }




}
