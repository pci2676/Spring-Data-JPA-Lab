package com.javabom.bomjpa.merge.service;

import com.javabom.bomjpa.merge.model.MergePerson;
import com.javabom.bomjpa.merge.model.MergePersonRepository;
import com.javabom.bomjpa.merge.model.MergePhone;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@RequiredArgsConstructor
@Service
public class MergeService {
    private final MergePersonRepository mergePersonRepository;

    @Transactional
    public void addPhone(Long id, List<String> numbers) {
        MergePerson mergePerson = mergePersonRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("사람 없음"));

        numbers.forEach(number -> mergePerson.add(new MergePhone(number)));
    }

    @Transactional
    public void addPhoneWithHelper(Long id, List<String> numbers) {
        MergePerson mergePerson = mergePersonRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("사람 없음"));

        numbers.forEach(number -> mergePerson.addWithHelper(new MergePhone(number)));
    }

    @Transactional
    public void addPhoneWithHelperAndSave(Long id, List<String> numbers) {
        MergePerson mergePerson = mergePersonRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("사람 없음"));

        numbers.forEach(number -> mergePerson.addWithHelper(new MergePhone(number)));

        mergePersonRepository.save(mergePerson);
    }
}
