package com.javabom.bomjpa.merge.model;


import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class MergePhone {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long seq;

    private String number;

    @ManyToOne(fetch = FetchType.LAZY)
    private MergePerson mergePerson;

    public MergePhone(String number) {
        this.number = number;
    }

    public void place(MergePerson mergePerson) {
        this.mergePerson = mergePerson;
    }
}
