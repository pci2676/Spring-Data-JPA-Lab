package com.javabom.bomjpa.repository;

import com.javabom.bomjpa.service.dto.ProductPaginationDto;
import com.javabom.bomjpa.service.dto.QProductPaginationDto;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;

import static com.javabom.bomjpa.model.QProduct.product;

@RequiredArgsConstructor
@Repository
public class ProductQueryRepository {
    private final JPAQueryFactory queryFactory;

    public List<ProductPaginationDto> paginationLegacy(String name, int pageNumber, int pageSize) {
        return queryFactory
                .select(new QProductPaginationDto(product.id, product.name, product.price))
                .from(product)
                .where(
                        product.name.like(name + "%")
                )
                .orderBy(product.id.desc())
                .offset(pageNumber * pageSize)
                .limit(pageSize)
                .fetch();
    }

    public List<ProductPaginationDto> paginationNoOffsetBuilder(Long id, String name, int pageSize) {
        return queryFactory
                .select(new QProductPaginationDto(product.id, product.name, product.price))
                .from(product)
                .where(
                        lessThanProductId(id),
                        product.name.like(name + "%")
                )
                .orderBy(product.id.desc())
                .limit(pageSize)
                .fetch();
    }

    private BooleanExpression lessThanProductId(Long productId) {
        if (Objects.isNull(productId)) {
            return null;
        }
        return product.id.lt(productId);
    }
}
