package com.xib.assessment.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "managers")
public class Manager implements Serializable {

    private static final long serialVersionUID = -7316726349419598095L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @ManyToMany(fetch = FetchType.EAGER,
            cascade = {
                    CascadeType.MERGE
            }, mappedBy = "teamManagers")
    @JsonIgnoreProperties( value = "teamManagers", allowSetters=true)
    private Set<Team> teamsBeingManaged = new HashSet<>();

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

    public Set<Team> getTeamsBeingManaged() {
        return teamsBeingManaged;
    }

    public void setTeamsBeingManaged(Set<Team> teamsBeingManaged) {
        this.teamsBeingManaged = teamsBeingManaged;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;

        if (!(o instanceof Manager))
            return false;

        Manager tag = (Manager) o;
        return Objects.equals(name, tag.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }


}
