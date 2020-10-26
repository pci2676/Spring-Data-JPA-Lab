package com.javabom.bomjpa.repository;

import com.javabom.bomjpa.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
