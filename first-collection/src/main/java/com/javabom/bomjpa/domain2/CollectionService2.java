package com.javabom.bomjpa.domain2;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class CollectionService2 {
    private final Person2Repository person2Repository;

    @Transactional
    public Long addTeams2(Long personId, List<String> teamNames) {
        Person2 person2 = person2Repository.findById(personId)
                .orElseThrow(RuntimeException::new);

        Teams2 teams2 = teamNames.stream()
                .map(Team2::new)
                .collect(Collectors.collectingAndThen(toList(), Teams2::new));

        person2.add(teams2);

        return person2.getId();
    }
}
