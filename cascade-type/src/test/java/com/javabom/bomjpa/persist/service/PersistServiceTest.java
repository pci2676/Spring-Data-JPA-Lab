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
import static org.junit.jupiter.api.Assertions.assertAll;

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
        persistPhoneRepository.deleteAll();
        persistPersonRepository.deleteAll();
    }

    @DisplayName("Persist 로 영속화는 되지만 도우미 메서드는 외래키 저장을 위해 필요하다. 더티체킹은 된다.")
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

        assertAll("FK가 저장되지 않아 객체 그래프 탐색이 불가능하다.",
                () -> assertThat(phones).hasSize(4),
                () -> assertThat(phones.get(0).getPersistPerson()).isNull()
        );

        List<PersistPhone> allPersistPhone = persistPhoneRepository.findAll();
        assertAll("하지만 영속화는 되어있다.",
                () -> assertThat(allPersistPhone).hasSize(4)
        );

        List<PersistPhone> findPersistPhones = persistPhoneRepository.findAllByPersistPerson(persistPerson);
        assertAll("그렇지만 외래키는 null인 상태이다.",
                () -> assertThat(findPersistPhones).isEmpty()
        );
    }

    @DisplayName("Persist 도 결국 외래키 때문에 도우미 메서드는 필요하다. 더티체킹은 된다.")
    @Test
    void addPhoneWithHelper() {
        PersistPerson persistPerson = persistPersonRepository.save(new PersistPerson("name"));

        persistService.addPhoneWithHelper(persistPerson.getId(), Arrays.asList(
                "010",
                "011",
                "012",
                "013"
        ));

        List<PersistPhone> phones = persistPhoneRepository.findAll();

        assertAll("객체 그래프의 탐색이 가능하다.",
                () -> assertThat(phones).hasSize(4),
                () -> assertThat(phones.get(0).getPersistPerson()).isNotNull()
        );

        List<PersistPhone> findPersistPhones = persistPhoneRepository.findAllByPersistPerson(persistPerson);
        assertAll("외래키도 정상적으로 저장되어있다.",
                () -> assertThat(findPersistPhones).isNotEmpty()
        );
    }
}