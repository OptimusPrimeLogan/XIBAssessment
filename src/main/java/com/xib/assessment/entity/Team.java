package com.xib.assessment.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "teams")
public class Team implements Serializable {

    private static final long serialVersionUID = 7229694087203562500L;
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long id;
    private String name;


    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.MERGE
            })
    @JoinTable(name = "team_managers",
            joinColumns = { @JoinColumn(name = "team_id") },
            inverseJoinColumns = { @JoinColumn(name = "manager_id") })
    @JsonIgnoreProperties( value = "teamsBeingManaged", allowSetters=true)
    private Set<Manager> teamManagers = new HashSet<>();

    public Team() {}

    public Team(String name){
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTeamManagers(Set<Manager> teamManagers) {
        this.teamManagers = teamManagers;
    }

    public Set<Manager> getTeamManagers() {
        return teamManagers;
    }

    public void addManager(Manager manager) {
        teamManagers.add(manager);
        manager.getTeamsBeingManaged().add(this);
    }

    public void removeManager(Manager manager) {
        teamManagers.remove(manager);
        manager.getTeamsBeingManaged().remove(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;

        if (!(o instanceof Team)) return false;

        return id != null && id.equals(((Team) o).getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
