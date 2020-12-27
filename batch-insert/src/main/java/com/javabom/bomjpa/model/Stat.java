package com.javabom.bomjpa.model;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity
public class Stat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "stat", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<StatDetail> statDetails = new ArrayList<>();

    public void addDetail(List<StatDetail> statDetails) {
        for (StatDetail statDetail : statDetails) {
            statDetail.placeStat(this);
            this.statDetails.add(statDetail);
        }
    }
}
