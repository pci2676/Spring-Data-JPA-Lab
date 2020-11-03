package com.javabom.bomjpa.repository;

import com.javabom.bomjpa.model.Product;
import com.javabom.bomjpa.service.dto.ProductPaginationDto;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class ProductQueryRepositoryTest {

    @Autowired
    private ProductQueryRepository productQueryRepository;

    @Autowired
    private ProductRepository productRepository;

    @AfterEach
    void tearDown() {
        productRepository.deleteAllInBatch();
    }

    @DisplayName("일반적인 페이지 네이션 항상 제대로 계산된 페이지네이션 결과를 내려준다.")
    @Test
    void paginationCount() {
        //given
        String prefixName = "상품";
        for (int i = 0; i < 85; i++) {
            productRepository.save(new Product(prefixName + i, i * 100L));
        }

        //when
        PageRequest pageRequest = PageRequest.of(1, 10);
        Page<ProductPaginationDto> results = productQueryRepository.paginationCount(pageRequest, prefixName);

        //then
        assertThat(results.getTotalPages()).isEqualTo(9);
        assertThat(results.getTotalElements()).isEqualTo(85);
    }

    @DisplayName("검색버튼으로 검색시 고정된 페이지 갯수를 내려준다.")
    @Test
    void paginationCountSearchBtn() {
        //given
        String prefixName = "상품";
        for (int i = 0; i < 85; i++) {
            productRepository.save(new Product(prefixName + i, i * 100L));
        }

        //when
        PageRequest pageRequest = PageRequest.of(1, 10);
        Page<ProductPaginationDto> results = productQueryRepository.paginationCountSearchBtn(pageRequest, prefixName, true);

        //then
        assertThat(results.getTotalPages()).isEqualTo(10);
        assertThat(results.getPageable().getPageNumber()).isEqualTo(1);
        assertThat(results.getTotalElements()).isEqualTo(100);
    }

    @DisplayName("검색버튼이 아닌 검색시 제대로된 페이지 결과를 내려준다.")
    @Test
    void paginationCountSearchBtn2() {
        //given
        String prefixName = "상품";
        for (int i = 0; i < 85; i++) {
            productRepository.save(new Product(prefixName + i, i * 100L));
        }

        //when
        PageRequest pageRequest = PageRequest.of(10, 10);
        Page<ProductPaginationDto> results = productQueryRepository.paginationCountSearchBtn(pageRequest, prefixName, false);

        //then
        assertThat(results.getTotalPages()).isEqualTo(9);
        assertThat(results.getPageable().getPageNumber()).isEqualTo(9);
        assertThat(results.getTotalElements()).isEqualTo(85);
    }
}