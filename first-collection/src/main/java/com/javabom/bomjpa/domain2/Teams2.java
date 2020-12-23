package com.javabom.bomjpa.domain2;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.util.List;

@Getter
@NoArgsConstructor
@Embeddable
public class Teams2 {

    @OneToMany(mappedBy = "person", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Team2> teams;

    public Teams2(List<Team2> teams) {
        this.teams = teams;
    }

    public void add(Person2 person, Teams2 teams) {
        for (Team2 team : teams.teams) {
            team.placePerson(person);
            this.teams.add(team);
        }
    }
}
