package com.javabom.bomjpa.merge.service;

import com.javabom.bomjpa.merge.model.MergePerson;
import com.javabom.bomjpa.merge.model.MergePersonRepository;
import com.javabom.bomjpa.merge.model.MergePhone;
import com.javabom.bomjpa.merge.model.MergePhoneRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class MergeServiceTest {
    @Autowired
    private MergeService mergeService;

    @Autowired
    private MergePersonRepository mergePersonRepository;

    @Autowired
    private MergePhoneRepository mergePhoneRepository;

    @AfterEach
    void tearDown() {
        mergePhoneRepository.deleteAll();
        mergePersonRepository.deleteAll();
    }

    @DisplayName("Merge는 도우미 메서드 없이 영속화 되지 않는다.")
    @Test
    void addPhone() {
        MergePerson mergePerson = mergePersonRepository.save(new MergePerson("name"));

        mergeService.addPhone(mergePerson.getId(), Arrays.asList(
                "010",
                "011",
                "012",
                "013"
        ));

        List<MergePhone> mergePhones = mergePhoneRepository.findAll();

        assertThat(mergePhones).hasSize(0);
    }

    @DisplayName("도우미 메서드가 있다면 영속화 한다.")
    @Test
    void addPhoneWithHelper() {
        MergePerson mergePerson = mergePersonRepository.save(new MergePerson("name"));

        mergeService.addPhoneWithHelper(mergePerson.getId(), Arrays.asList(
                "010",
                "011",
                "012",
                "013"
        ));

        List<MergePhone> mergePhones = mergePhoneRepository.findAll();

        assertThat(mergePhones).hasSize(4);
    }
}