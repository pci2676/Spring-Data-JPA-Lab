package com.javabom.bomjpa.merge.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class MergePerson {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToMany(mappedBy = "mergePerson", cascade = CascadeType.MERGE)
    private List<MergePhone> mergePhones = new ArrayList<>();

    public MergePerson(String name) {
        this.name = name;
    }

    public void add(MergePhone mergePhone) {
        this.mergePhones.add(mergePhone);
    }

    public void addWithHelper(MergePhone mergePhone) {
        this.mergePhones.add(mergePhone);
        mergePhone.place(this);
    }
}
