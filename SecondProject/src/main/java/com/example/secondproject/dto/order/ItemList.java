package com.example.secondproject.dto.order;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ItemList {

    private Long id;
    private String name;
    private int price;
    private Long categoryId;
}
