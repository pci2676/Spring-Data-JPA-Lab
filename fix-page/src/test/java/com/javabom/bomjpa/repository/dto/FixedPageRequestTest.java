package com.javabom.bomjpa.repository.dto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import static org.assertj.core.api.Assertions.assertThat;

class FixedPageRequestTest {

    @DisplayName("요청 페이지 보다 전체 검색 결과 적으면 마지막 페이지를 보여준다.")
    @ParameterizedTest
    @CsvSource({
            "10, 100, 10",
            "10, 101, 10",
            "10, 91, 10",
            "10, 90, 9",
            "10, 79, 8"})
    void test1(int pageNo, long totalCount, int expectedPageNo) {
        //given
        Pageable pageRequest = PageRequest.of(pageNo, 10);

        //when
        Pageable result = new FixedPageRequest(pageRequest, totalCount);

        //then
        assertThat(result.getPageNumber()).isEqualTo(expectedPageNo);
    }

}