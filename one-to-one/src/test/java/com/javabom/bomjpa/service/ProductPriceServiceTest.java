package com.javabom.bomjpa.service;

import com.javabom.bomjpa.model.Price;
import com.javabom.bomjpa.model.PriceRepository;
import com.javabom.bomjpa.model.Product;
import com.javabom.bomjpa.model.ProductRepository;
import org.hibernate.proxy.HibernateProxy;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@SpringBootTest
class ProductPriceServiceTest {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private PriceRepository priceRepository;

    @Autowired
    private ProductPriceService productPriceService;

    @BeforeEach
    void setUp() {
        List<Price> priceList = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            Product product = new Product("상품 " + i);
            Price price = new Price((long) i, product);
            priceList.add(price);
        }
        priceRepository.saveAll(priceList);
    }

    @DisplayName("이 테스트 클래스에서는 테이블에 100개의 데이터가 저장되어있다.")
    @Test
    void setUpTest() {
        assertThat(priceRepository.findAll()).hasSize(100);
        assertThat(productRepository.findAll()).hasSize(100);
    }

    @DisplayName("Price 를 조회하면 Product 는 정상적으로 레이지 로딩이 된다.")
    @Test
    void findAllPriceTest() {
        List<Price> allPrice = productPriceService.findAllPrice();
        Product product = allPrice.get(0).getProduct();

        assertThat(product).isInstanceOf(HibernateProxy.class);
    }

    @DisplayName("Product 는 조회하면 Price 는 레이지 로딩이 되지 않는다 !!!")
    @Test
    void findAllProductTest() {
        List<Product> allProduct = productPriceService.findAllProduct();
        Price price = allProduct.get(0).getPrice();

        assertAll(
                "0. 주인이 아닌 객체에서 참조객체를 조회할 때의 상황이다." +
                        "1. nullable 하지 못한 1:1 관계에서 참조객체가 optional = false 가 가능하지 않으면 안된다." +
                        "2. 단방향이어야 한다. 자식이 FK를 가지고 있으면 Lazy 로딩이 안된다." +
                        "3. @PrimaryKeyJoin 은 허용되지 않는다. 즉, 부모-자식간의 JoinColumn 이 PK 인 경우이다." +
                        "4. 조회를 해야할때 2번의 문제를 해결하기 위해서는 데이터베이스에서 외래키의 위치를 부모테이블로 옮겨주어야 한다.",
                () -> assertThat(price).isNotInstanceOf(HibernateProxy.class),
                () -> assertThat(price).isInstanceOf(Price.class)
        );
    }

    @AfterEach
    void tearDown() {
        priceRepository.deleteAll();
        productRepository.deleteAll();
    }
}