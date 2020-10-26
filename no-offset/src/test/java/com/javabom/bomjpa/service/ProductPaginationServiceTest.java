package com.javabom.bomjpa.service;

import com.javabom.bomjpa.NoOffsetApplication;
import com.javabom.bomjpa.model.Product;
import com.javabom.bomjpa.repository.ProductRepository;
import com.javabom.bomjpa.service.dto.ProductPaginationDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = NoOffsetApplication.class)
class ProductPaginationServiceTest {

    @Autowired
    private ProductPaginationService productPaginationService;
    @Autowired
    private ProductRepository productRepository;

    @DisplayName("매번 페이지 전체를 읽는다.")
    @Test
    void legacyTest() {
        //given
        String prefixName = "상품";

        for (int i = 1; i <= 30; i++) {
            productRepository.save(new Product("상품" + i, (long) i));
        }

        //when
        List<ProductPaginationDto> books = productPaginationService.getProductPaginationLegacy(prefixName, 1, 10); // pageNo는 0부터 시작이라 1이면 두번째 페이지 조회

        //then
        assertThat(books).hasSize(10);
        assertThat(books.get(0).getName()).isEqualTo("상품20");
        assertThat(books.get(9).getName()).isEqualTo("상품11");
    }

    @DisplayName("where 절로 인해 페이지 전체를 읽지 않는다.")
    @Test
    void builderTest() {
        //given
        String prefixName = "상품";

        for (int i = 1; i <= 30; i++) {
            productRepository.save(new Product("상품" + i, (long) i));
        }

        //when
        List<ProductPaginationDto> books = productPaginationService.getProductPagination(null, prefixName, 10); // pageNo는 0부터 시작이라 1이면 두번째 페이지 조회

        //then
        assertThat(books).hasSize(10);
        assertThat(books.get(0).getName()).isEqualTo("상품30");
        assertThat(books.get(9).getName()).isEqualTo("상품21");
    }

}