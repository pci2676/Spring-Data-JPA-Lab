package com.javabom.bomjpa.repository;

import com.javabom.bomjpa.service.dto.ProductPaginationDto;
import com.javabom.bomjpa.service.dto.QProductPaginationDto;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.javabom.bomjpa.model.QProduct.product;

@RequiredArgsConstructor
@Repository
public class ProductQueryRepository {
    private final JPAQueryFactory queryFactory;

    public List<ProductPaginationDto> paginationLegacy(String name, int pageNumber, int pageSize) {
        return queryFactory
                .select(new QProductPaginationDto(product.id, product.name, product.money))
                .from(product)
                .where(
                        product.name.like(name + "%")
                )
                .orderBy(product.id.desc())
                .offset(pageNumber * pageSize)
                .limit(pageSize)
                .fetch();
    }
}
