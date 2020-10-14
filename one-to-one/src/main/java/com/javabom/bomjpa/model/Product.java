package com.javabom.bomjpa.model;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PRODUCT_ID")
    private Long id;
    private String name;

    @OneToOne(mappedBy = "product", fetch = FetchType.LAZY)
    @JoinColumn(name = "PRICE_ID")
    private Price price;

    public Product(final String name) {
        this.name = name;
    }

    @Builder
    public Product(final String name, final Price price) {
        this.name = name;
        this.price = price;
    }
}
