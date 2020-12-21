package com.javabom.bomjpa.domain;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class CollectionServiceTest {
    @Autowired
    private CollectionService collectionService;

    @Autowired
    private PersonRepository personRepository;

    @AfterEach
    void tearDown() {
        personRepository.deleteAll();
    }

    @DisplayName("final 멤법 변수로 일급컬렉션을 생성할 수 있다.")
    @Test
    void addTeams() {
        Person person = personRepository.save(new Person());

        Long personId = collectionService.addTeams(person.getId(), Arrays.asList("1", "2", "3"));

        Person findPerson = personRepository.findById(personId).orElseThrow(RuntimeException::new);

        assertThat(findPerson.getTeams().getTeams()).hasSize(3);
    }

}