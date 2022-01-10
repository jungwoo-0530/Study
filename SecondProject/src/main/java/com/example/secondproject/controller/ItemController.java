package com.example.secondproject.controller;

import com.example.secondproject.domain.order.Category;
import com.example.secondproject.domain.order.Item;
import com.example.secondproject.domain.user.Member;
import com.example.secondproject.dto.order.ItemCreateForm;
import com.example.secondproject.dto.order.ItemDetailDto;
import com.example.secondproject.dto.order.ItemList;
import com.example.secondproject.service.CategoryService;
import com.example.secondproject.service.ItemService;
import com.example.secondproject.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
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


    @GetMapping("/items/{categoryId}")
    public String items(@PathVariable("categoryId") Long categoryId,
                        Model model) {

        List<ItemList> itemLists = itemService.findItemsByCategoryId(categoryId);

        model.addAttribute("itemList", itemLists);

        return "/orders/item/itemList";
    }

    //DTO사용해야할 것. 나중에.
    @GetMapping("/items/{categoryId}/{itemId}")
    public String itemDetail(@PathVariable("itemId") Long itemId,
                             @PathVariable("categoryId") Long categoryId,
                             Model model) {

        Item item = itemService.findOneById(itemId);

        model.addAttribute("item", item);

        return "/orders/item/itemDetail";
    }

    @GetMapping("/provider/item/add")
    public String createItem(Model model) {

        List<String> categories = categoryService.findAllName();

        model.addAttribute("itemForm", new ItemCreateForm());
        model.addAttribute("categoryNames", categories);

        return "/orders/item/itemCreate";
    }

    @PostMapping("/provider/item/add")
    public String createItem(@ModelAttribute("itemForm") ItemCreateForm form,
                             Principal principal) {

        Category category = categoryService.findCategoryByName(form.getCategoryName());
        Member member = memberService.findByEmail(principal.getName());
        itemService.save(form, category, member);

        return "redirect:/";
    }

    /////////////////////////////
    //아이템 리스트. provider 전용.
    @GetMapping("/provider/items")
    public String itemList(Model model, Principal principal) {

        List<Item> items = itemService.findItemsByMemberEmail(principal.getName());
        model.addAttribute("itemList", items);
        return null;
    }

    //아이템 상세 정보.
    @GetMapping("/provider/items/{itemId}")
    public String itemDetailByProvider(@PathVariable("itemId")Long itemId,
                             Model model) {

        Item item = itemService.findOneById(itemId);
        ItemDetailDto itemDetailDto = new ItemDetailDto(item.getName(), item.getPrice(),
                item.getStock(), item.getContent());

        model.addAttribute("itemDto", itemDetailDto);

        return null;
    }
}
