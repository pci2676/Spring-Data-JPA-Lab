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
        table = "sequence_table",
        pkColumnName = "sequence_name",
        valueColumnName = "next_val",
        allocationSize = 1000
)
public class StatDetail {
    @Id
    @GeneratedValue(
            strategy = GenerationType.TABLE
            , generator = "GENERATOR_STAT_DETAIL"
    )
    private Long seq;

    private Long score;

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
