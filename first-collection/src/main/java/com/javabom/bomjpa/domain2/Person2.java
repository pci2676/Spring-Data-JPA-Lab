package com.javabom.bomjpa.domain2;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class Person2 {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private Teams2 teams2;

    public void add(Teams2 teams2) {
        this.teams2.add(this, teams2);
    }
}
