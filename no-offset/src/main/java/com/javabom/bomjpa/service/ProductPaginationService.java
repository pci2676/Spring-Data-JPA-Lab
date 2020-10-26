package com.javabom.bomjpa.service;

import com.javabom.bomjpa.repository.ProductQueryRepository;
import com.javabom.bomjpa.service.dto.ProductPaginationDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ProductPaginationService {

    private final ProductQueryRepository productQueryRepository;

    public List<ProductPaginationDto> getProductPaginationLegacy(String prefixName, int pageNumber, int pageSize) {
        return productQueryRepository.paginationLegacy(prefixName, pageNumber, pageSize);
    }

    public List<ProductPaginationDto> getProductPagination(final Long productId, final String prefixName, final int pageSize) {
        return productQueryRepository.paginationNoOffsetBuilder(productId, prefixName, pageSize);
    }
}
