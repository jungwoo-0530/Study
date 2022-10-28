package com.example.secondproject.dto.order;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.Lob;

@Data
@AllArgsConstructor
public class ItemDto {
    private Long id;
    private String name;
    private int price;
    @Lob
    private String content;
}
