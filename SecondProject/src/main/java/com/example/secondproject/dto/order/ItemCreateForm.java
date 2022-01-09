package com.example.secondproject.dto.order;

import lombok.Data;
import lombok.Getter;

import javax.persistence.Lob;

@Data
public class ItemCreateForm {

    private Long id;

    private String name;

    private int price;

    private int stock;

    private String categoryName;

    @Lob
    private String content;
}
