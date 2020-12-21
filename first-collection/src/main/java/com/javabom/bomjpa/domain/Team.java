package com.javabom.bomjpa.domain;


import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class Team {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    private Person person;

    public Team(String name) {
        this.name = name;
    }

    public void placePerson(Person person) {
        this.person = person;
    }
}
