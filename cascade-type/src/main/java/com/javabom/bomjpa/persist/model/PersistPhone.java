package com.javabom.bomjpa.persist.model;


import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class PersistPhone {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long seq;

    private String number;

    @ManyToOne(fetch = FetchType.LAZY)
    private PersistPerson persistPerson;

    public PersistPhone(String number) {
        this.number = number;
    }
}
