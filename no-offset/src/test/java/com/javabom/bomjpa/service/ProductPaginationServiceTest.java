package com.javabom.bomjpa.service;

import com.javabom.bomjpa.NoOffsetApplication;
import com.javabom.bomjpa.service.dto.ProductPaginationDto;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Instant;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("maria")
@SpringBootTest(classes = NoOffsetApplication.class)
class ProductPaginationServiceTest {

    @Autowired
    private ProductPaginationService productPaginationService;

    @DisplayName("매번 페이지 전체를 읽는다.")
    @Test
    void legacyTest() {
        //given
        String prefixName = "상품";
        long before = Instant.now().toEpochMilli();

        //when
        List<ProductPaginationDto> products = productPaginationService.getProductPaginationLegacy(prefixName, 100, 1000); // pageNo는 0부터 시작이라 1이면 두번째 페이지 조회

        long after = Instant.now().toEpochMilli();
        System.out.println("소요 시간 : " + (after - before) + "ms");

        //then
        assertThat(products).hasSize(1000);
        assertThat(products.get(0).getName()).isEqualTo("상품1900000");
        assertThat(products.get(999).getName()).isEqualTo("상품1899001");
    }

    @DisplayName("where 절로 인해 페이지 전체를 읽지 않는다.")
    @Test
    void builderTest() {
        //given
        String prefixName = "상품";
        long before = Instant.now().toEpochMilli();

        //when
        List<ProductPaginationDto> products = productPaginationService.getProductPagination(900001L, prefixName, 1000); // pageNo는 0부터 시작이라 1이면 두번째 페이지 조회

        long after = Instant.now().toEpochMilli();
        System.out.println("소요 시간 : " + (after - before) + "ms");

        //then
        assertThat(products).hasSize(1000);
        assertThat(products.get(0).getName()).isEqualTo("상품1900000");
        assertThat(products.get(999).getName()).isEqualTo("상품1899001");
    }

    @Disabled
    @Test
    void mockDataProvider() throws IOException {
        for (long i = 1; i <= 2; i++) {
            String fileName = "test_" + i + ".csv";
            File file = new File("./src/main/resources/" + fileName);
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file));

            StringBuilder sb = new StringBuilder();
            for (long j = 1; j <= 1000000; j++) {
                sb.append("상품").append(i).append(j)
                        .append(",")
                        .append(j)
                        .append(System.lineSeparator());
            }

            bufferedWriter.write(sb.toString());
            bufferedWriter.flush();
        }
    }
}