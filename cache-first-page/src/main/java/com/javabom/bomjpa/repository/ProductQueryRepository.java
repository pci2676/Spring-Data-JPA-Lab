package com.javabom.bomjpa.repository;

import com.javabom.bomjpa.model.Product;
import com.javabom.bomjpa.service.dto.ProductPaginationDto;
import com.javabom.bomjpa.service.dto.QProductPaginationDto;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.Querydsl;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;

import static com.javabom.bomjpa.model.QProduct.product;

@Repository
public class ProductQueryRepository extends QuerydslRepositorySupport {
    private final JPAQueryFactory queryFactory;

    public ProductQueryRepository(JPAQueryFactory queryFactory) {
        super(Product.class);
        this.queryFactory = queryFactory;
    }

    public Page<ProductPaginationDto> paginationCount(Long cachedCount, Pageable pageable, String name) {
        JPQLQuery<ProductPaginationDto> query = querydsl().applyPagination(pageable,
                queryFactory.select(
                        new QProductPaginationDto(
                                product.id,
                                product.name,
                                product.price
                        ))
                        .from(product)
                        .where(product.name.like(name + "%"))
                        .orderBy(product.id.desc())
        );

        List<ProductPaginationDto> results = query.fetch();
        // 프론트에서 넘겨준 count값을 사용할지 말지 결정하는 부분
        long totalCount = cachedCount != null ? cachedCount : query.fetchCount();

        return new PageImpl<>(results, pageable, totalCount);
    }

    private Querydsl querydsl() {
        return Objects.requireNonNull(getQuerydsl());
    }
}
