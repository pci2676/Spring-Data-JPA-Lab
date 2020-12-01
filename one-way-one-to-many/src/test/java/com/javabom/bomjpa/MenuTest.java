package com.javabom.bomjpa;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DataJpaTest
class MenuTest {

    @Autowired
    private MenuRepository menuRepository;

    @DisplayName("단방향일 때 MenuProduct에 null 값으로 insert 할 때 문제가 발생한다.")
    @Test
    void addMenuProduct() {
        //given
        Menu menu = new Menu();
        MenuProduct menuProduct = new MenuProduct();
        menu.addMenuProduct(menuProduct);

        //then
        assertThatThrownBy(() -> menuRepository.save(menu))
                .isInstanceOf(DataIntegrityViolationException.class)
                .hasMessageContaining("constraint [null]");
    }
}