package com.example.secondproject.domain.order;

import com.example.secondproject.domain.user.Member;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ITEM_ID")
    private Long id;

    private String name;
    private int price;
    private int stock;
    @Lob
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CATEGORY_ID")
    private Category category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MEMBER_ID")
    private Member member;


    public Item() {

    }

    public Item(String name, int price, int stock, String content, Category category, Member member) {
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.content = content;
        this.category = category;
        this.member = member;
    }
}
