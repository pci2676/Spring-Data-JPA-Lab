package com.javabom.bomjpa.persist.service;

import com.javabom.bomjpa.persist.model.PersistPerson;
import com.javabom.bomjpa.persist.model.PersistPersonRepository;
import com.javabom.bomjpa.persist.model.PersistPhone;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@RequiredArgsConstructor
@Service
public class PersistService {
    private final PersistPersonRepository persistPersonRepository;

    @Transactional
    public void addPhone(Long id, List<String> numbers) {
        PersistPerson persistPerson = persistPersonRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("사람 없음"));

        numbers.forEach(number -> persistPerson.add(new PersistPhone(number)));
    }

    @Transactional
    public void addPhoneWithHelper(Long id, List<String> numbers) {
        PersistPerson persistPerson = persistPersonRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("사람 없음"));

        numbers.forEach(number -> persistPerson.addWithHelper(new PersistPhone(number)));
    }
}
