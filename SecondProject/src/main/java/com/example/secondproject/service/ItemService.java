package com.example.secondproject.service;

import com.example.secondproject.domain.order.Category;
import com.example.secondproject.domain.order.Item;
import com.example.secondproject.domain.user.Member;
import com.example.secondproject.dto.order.ItemCreateForm;
import com.example.secondproject.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

    @Transactional
    public void save(ItemCreateForm form, Category category, Member member) {

        Item item = new Item(form.getName(), form.getPrice(),
                form.getStock(), form.getContent(), category, member);

        itemRepository.save(item);
    }
}
