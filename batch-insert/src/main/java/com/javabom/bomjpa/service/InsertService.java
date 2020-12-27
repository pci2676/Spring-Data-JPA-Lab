package com.javabom.bomjpa.service;

import com.javabom.bomjpa.model.Stat;
import com.javabom.bomjpa.model.StatDetail;
import com.javabom.bomjpa.model.StatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class InsertService {
    private final StatRepository statRepository;

    @Transactional
    public void insert(Long statId, List<Long> scores) {
        Stat stat = statRepository.findById(statId)
                .orElseThrow(RuntimeException::new);

        List<StatDetail> statDetails = scores.stream()
                .map(StatDetail::new)
                .collect(Collectors.toList());
        stat.addDetail(statDetails);
    }
}
