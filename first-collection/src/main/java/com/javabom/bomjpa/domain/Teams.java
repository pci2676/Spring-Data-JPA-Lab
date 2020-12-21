package com.javabom.bomjpa.domain;

import lombok.Getter;

import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Getter
@Embeddable
public class Teams {

    @OneToMany(mappedBy = "person", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<Team> teams;

    protected Teams() {
        this.teams = new ArrayList<>();
    }

    public Teams(List<Team> teams) {
        this.teams = teams;
    }

    public void add(Person person, Teams teams) {
        for (Team team : teams.teams) {
            team.placePerson(person);
            this.teams.add(team);
        }
    }
}
