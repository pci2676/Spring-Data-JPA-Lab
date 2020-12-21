package com.javabom.bomjpa.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private Teams teams;

    public void add(Teams teams) {
        this.teams.add(this, teams);
    }
}
