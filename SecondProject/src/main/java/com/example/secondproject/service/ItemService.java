package com.example.secondproject.service;

import com.example.secondproject.domain.order.Category;
import com.example.secondproject.domain.order.Item;
import com.example.secondproject.domain.user.Member;
import com.example.secondproject.dto.order.ItemDto;
import com.example.secondproject.dto.order.provider.ItemCreateForm;
import com.example.secondproject.dto.order.ItemList;
import com.example.secondproject.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

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

    @Transactional(readOnly = true)
    public List<Item> findItemsByMemberEmail(String email) {
        return itemRepository.findItemsByMemberEmail(email);
    }

    @Transactional(readOnly = true)
    public Item findOneById(Long id) {
        return itemRepository.findById(id).orElseThrow(NoSuchElementException::new);
    }

    @Transactional(readOnly = true)
    public ItemDto findItemDtoById(Long id) {
        Item item = itemRepository.findById(id).orElseThrow(NoSuchElementException::new);
        return new ItemDto(id, item.getName(), item.getPrice(),
                item.getContent());
    }


    @Transactional(readOnly = true)
    public List<ItemList> findItemsByCategoryId(Long categoryId) {

        List<Item> items = itemRepository.findItemsByCategoryId(categoryId);
        List<ItemList> itemLists = new ArrayList<>();
        for (Item item : items) {
            itemLists.add(new ItemList(item.getId(), item.getName(), item.getPrice(),
                    categoryId));
        }
        return itemLists;
    }
}
