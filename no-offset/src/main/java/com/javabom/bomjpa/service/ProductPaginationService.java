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

    public List<ProductPaginationDto> getProductPaginationLegacy(String name, int pageNumber, int pageSize) {
        return productQueryRepository.paginationLegacy(name, pageNumber, pageSize);
    }

}
