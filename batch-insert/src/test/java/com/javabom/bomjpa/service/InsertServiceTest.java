package com.javabom.bomjpa.service;

import com.javabom.bomjpa.model.Stat;
import com.javabom.bomjpa.model.StatDetailRepository;
import com.javabom.bomjpa.model.StatRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.StopWatch;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class InsertServiceTest {

    @Autowired
    private InsertService insertService;

    @Autowired
    private StatRepository statRepository;

    @Autowired
    private StatDetailRepository statDetailRepository;

    @AfterEach
    void tearDown() {
        statDetailRepository.deleteAllInBatch();
        statRepository.deleteAllInBatch();
    }

    @Test
    void insert() {
        //given
        Stat save = statRepository.save(new Stat());

        List<Long> scores = new ArrayList<>();
        for (long score = 1L; score <= 10_000; score++) {
            scores.add(score);
        }
        StopWatch stopWatch = new StopWatch();

        //when
        stopWatch.start();
        insertService.insert(save.getId(), scores);
        stopWatch.stop();

        //then
        long insertTime = stopWatch.getTotalTimeMillis();
        System.out.println("insert time : " + insertTime + "ms");
    }
}
