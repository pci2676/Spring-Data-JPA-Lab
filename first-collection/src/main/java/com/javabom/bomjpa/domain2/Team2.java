package com.javabom.bomjpa.domain2;


import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class Team2 {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    private Person2 person;

    public Team2(String name) {
        this.name = name;
    }

    public void placePerson(Person2 person) {
        this.person = person;
    }
}
