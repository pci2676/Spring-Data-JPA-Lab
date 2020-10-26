package com.javabom.bomjpa.service;

import com.javabom.bomjpa.NoOffsetApplication;
import com.javabom.bomjpa.repository.ProductRepository;
import com.javabom.bomjpa.service.dto.ProductPaginationDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@SpringBootTest(classes = NoOffsetApplication.class)
class ProductPaginationServiceTest {

    @Autowired
    private ProductPaginationService productPaginationService;
    @Autowired
    private ProductRepository productRepository;

    @Test
    void getProductPaginationLegacy() {
        LocalDateTime before = LocalDateTime.now();
        List<ProductPaginationDto> paginationDtos = productPaginationService.getProductPaginationLegacy("상품", 10000, 10);
        LocalDateTime after = LocalDateTime.now();
        Duration between = Duration.between(before, after);
        System.out.println(between.get(ChronoUnit.SECONDS));
    }
}