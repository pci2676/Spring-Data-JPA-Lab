package com.javabom.bomjpa.service;

import com.javabom.bomjpa.repository.ProductQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ProductPaginationService {

    private final ProductQueryRepository productQueryRepository;


}
