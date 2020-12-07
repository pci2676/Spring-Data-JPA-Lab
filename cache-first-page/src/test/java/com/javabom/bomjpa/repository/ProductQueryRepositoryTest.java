package com.javabom.bomjpa.repository;

import com.javabom.bomjpa.model.Product;
import com.javabom.bomjpa.service.dto.ProductPaginationDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class ProductQueryRepositoryTest {
    private static final String prefixName = "상품이름";

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductQueryRepository productQueryRepository;

    @BeforeEach
    void setUp() {
        for (long i = 1; i <= 30; i++) {
            productRepository.save(Product.builder()
                    .name(prefixName + i)
                    .price(i)
                    .build());
        }
    }

    @Test
    void paginationCount() {
        PageRequest pageRequest = PageRequest.of(1, 10);
        Long cachedCount = 100L;
        Page<ProductPaginationDto> page = productQueryRepository.paginationCount(cachedCount, pageRequest, prefixName);

        //then
        assertThat(page.getTotalElements()).isEqualTo(cachedCount);
    }
}