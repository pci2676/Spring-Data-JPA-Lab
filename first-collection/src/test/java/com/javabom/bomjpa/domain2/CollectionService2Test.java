package com.javabom.bomjpa.domain2;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class CollectionService2Test {

    @Autowired
    private CollectionService2 collectionService2;

    @Autowired
    private Person2Repository person2Repository;

    @AfterEach
    void tearDown() {
        person2Repository.deleteAll();
    }

    @DisplayName("멤버변수 필드에서 빈 컬렉션으로 초기화하지 않아도 생성할 수 있다.")
    @Test
    void addTeams2() {
        Person2 person2 = person2Repository.save(new Person2());

        Long personId = collectionService2.addTeams2(person2.getId(), Arrays.asList("1", "2", "3"));

        Person2 findPerson2 = person2Repository.findById(personId).orElseThrow(RuntimeException::new);

        assertThat(findPerson2.getTeams2().getTeams()).hasSize(3);
    }
}