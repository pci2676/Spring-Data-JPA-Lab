package com.javabom.bomjpa.repository;

import com.javabom.bomjpa.model.Product;
import com.javabom.bomjpa.repository.dto.FixedPageRequest;
import com.javabom.bomjpa.service.dto.ProductPaginationDto;
import com.javabom.bomjpa.service.dto.QProductPaginationDto;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQuery;
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

    public Page<ProductPaginationDto> paginationCount(Pageable pageable, String name) {
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
        long totalCount = query.fetchCount();

        return new PageImpl<>(results, pageable, totalCount);
    }

    public Page<ProductPaginationDto> paginationCountSearchBtn(Pageable pageable, String name, boolean useSearchBtn) {
        JPAQuery<ProductPaginationDto> query = queryFactory.select(
                new QProductPaginationDto(
                        product.id,
                        product.name,
                        product.price
                ))
                .from(product)
                .where(product.name.like(name + "%"))
                .orderBy(product.id.desc());

        //실제 결과
        JPQLQuery<ProductPaginationDto> pagination = querydsl().applyPagination(pageable, query);

        if (useSearchBtn) {
            int fixedPageCount = 10 * pageable.getPageSize();
            return new PageImpl<>(pagination.fetch(), pageable, fixedPageCount);
        }

        //실제 전체 조회 갯수
        long totalCount = pagination.fetchCount();
        Pageable pageRequest = new FixedPageRequest(pageable, totalCount);

        return new PageImpl<>(querydsl().applyPagination(pageRequest, query).fetch(), pageRequest, totalCount);
    }

    private Querydsl querydsl() {
        return Objects.requireNonNull(getQuerydsl());
    }
}
