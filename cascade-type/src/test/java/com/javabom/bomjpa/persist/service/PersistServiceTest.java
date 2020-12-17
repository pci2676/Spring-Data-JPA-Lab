package com.javabom.bomjpa.persist.service;

import com.javabom.bomjpa.persist.model.PersistPerson;
import com.javabom.bomjpa.persist.model.PersistPersonRepository;
import com.javabom.bomjpa.persist.model.PersistPhone;
import com.javabom.bomjpa.persist.model.PersistPhoneRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class PersistServiceTest {

    @Autowired
    private PersistService persistService;

    @Autowired
    private PersistPersonRepository persistPersonRepository;

    @Autowired
    private PersistPhoneRepository persistPhoneRepository;

    @AfterEach
    void tearDown() {
        persistPhoneRepository.deleteAllInBatch();
        persistPersonRepository.deleteAllInBatch();
    }

    @DisplayName("Persist 로 설정하면 도우미 메서드가 필요없다.")
    @Test
    void addPhone() {
        PersistPerson persistPerson = persistPersonRepository.save(new PersistPerson("name"));

        persistService.addPhone(persistPerson.getId(), Arrays.asList(
                "010",
                "011",
                "012",
                "013"
        ));

        List<PersistPhone> phones = persistPhoneRepository.findAll();

        assertThat(phones).hasSize(4);
    }
}