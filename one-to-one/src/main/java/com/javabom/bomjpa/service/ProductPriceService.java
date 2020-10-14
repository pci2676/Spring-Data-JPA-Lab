package com.javabom.bomjpa.service;

import com.javabom.bomjpa.model.Price;
import com.javabom.bomjpa.model.PriceRepository;
import com.javabom.bomjpa.model.Product;
import com.javabom.bomjpa.model.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ProductPriceService {
    private final ProductRepository productRepository;
    private final PriceRepository priceRepository;

    public List<Product> findAllProduct() {
        return productRepository.findAll();
    }

    public List<Price> findAllPrice() {
        return priceRepository.findAll();
    }
}
