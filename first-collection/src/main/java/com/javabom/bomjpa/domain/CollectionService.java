package com.javabom.bomjpa.domain;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class CollectionService {

    private final PersonRepository personRepository;

    @Transactional
    public Long addTeams(Long personId, List<String> teamNames) {
        Person person = personRepository.findById(personId)
                .orElseThrow(RuntimeException::new);

        Teams teams = teamNames.stream()
                .map(Team::new)
                .collect(Collectors.collectingAndThen(toList(), Teams::new));

        person.add(teams);

        return person.getId();
    }
}
