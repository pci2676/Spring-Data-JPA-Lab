package com.javabom.bomjpa.repository;

import com.javabom.bomjpa.model.Product;
import com.javabom.bomjpa.service.dto.ProductPaginationDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
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

    @DisplayName("프론트에서 캐싱한 값을 넘기면 캐싱한 값을 카운트로 내려준다")
    @Test
    void paginationCountWithCached() {
        PageRequest pageRequest = PageRequest.of(1, 10);
        Long cachedCount = 100L;
        Page<ProductPaginationDto> page = productQueryRepository.paginationCount(cachedCount, pageRequest, prefixName);

        //then
        assertThat(page.getTotalElements()).isEqualTo(cachedCount);
    }

    @DisplayName("캐시를 하지 않으면 실제 값을 조회해서 내려준다.")
    @Test
    void paginationCountWithoutCached() {
        PageRequest pageRequest = PageRequest.of(1, 10);
        Long cachedCount = null;
        Page<ProductPaginationDto> page = productQueryRepository.paginationCount(cachedCount, pageRequest, prefixName);

        //then
        assertThat(page.getTotalElements()).isEqualTo(30);
    }
}