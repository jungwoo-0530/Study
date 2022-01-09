package com.example.secondproject.dto.order;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.Lob;

@Data
@AllArgsConstructor
public class ItemDetailDto {
    private String name;
    private int price;
    private int stock;
    @Lob
    private String content;
}
