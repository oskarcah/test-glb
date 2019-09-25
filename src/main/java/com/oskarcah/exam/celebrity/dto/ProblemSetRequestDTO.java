package com.oskarcah.exam.celebrity.dto;

import java.io.Serializable;
import java.util.List;

public class ProblemSetRequestDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;

    private List<String> people;

    private List<PeopleKnowRelationDTO> relations;

    public ProblemSetRequestDTO() {
        super();
    }

    public ProblemSetRequestDTO(List<String> people, List<PeopleKnowRelationDTO> relations) {
        this.people = people;

        this.relations = relations;
    }

    public List<String> getPeople() {
        return people;
    }

    public void setPeople(List<String> people) {
        this.people = people;
    }

    public List<PeopleKnowRelationDTO> getRelations() {
        return relations;
    }

    public void setRelations(List<PeopleKnowRelationDTO> relations) {
        this.relations = relations;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
