package com.javabom.bomjpa.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@TableGenerator(
        name = "GENERATOR_STAT_DETAIL",
        table = "stat_sequence",
        pkColumnName = "sequence_name",
        valueColumnName = "next_val",
        allocationSize = 100
)
public class StatDetail {
    @Id
    @GeneratedValue(
            strategy = GenerationType.TABLE
            , generator = "GENERATOR_STAT_DETAIL"
    )
    private Long seq;

    private Long score;
    private Long score1;
    private Long score2;
    private Long score3;
    private Long score4;
    private Long score5;
    private Long score6;
    private Long score7;
    private Long score8;
    private Long score9;

    @ManyToOne
    @JoinColumn(name = "stat_id")
    private Stat stat;

    public StatDetail(Long score) {
        this.score = score;
    }

    public void placeStat(Stat stat) {
        this.stat = stat;
    }
}
