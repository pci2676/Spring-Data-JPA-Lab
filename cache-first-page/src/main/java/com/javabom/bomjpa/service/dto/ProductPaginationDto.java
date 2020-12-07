package com.javabom.bomjpa.service.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;

@Getter
public class ProductPaginationDto {
    private final Long id;
    private final String name;
    private final Long price;

    @QueryProjection
    public ProductPaginationDto(final Long id, final String name, final Long price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }
}
