package com.javabom.bomjpa.persist.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class PersistPerson {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToMany(mappedBy = "persistPerson", cascade = CascadeType.PERSIST)
    private List<PersistPhone> persistPhones = new ArrayList<>();

    public PersistPerson(String name) {
        this.name = name;
    }

    public void add(PersistPhone persistPhone) {
        this.persistPhones.add(persistPhone);
    }

    public void addWithHelper(PersistPhone persistPhone) {
        this.persistPhones.add(persistPhone);
        persistPhone.placePerson(this);
    }
}
