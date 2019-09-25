package com.oskarcah.exam.celebrity.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class ProblemSolutionResponseDTO implements Serializable {

    private static  final long serialVersionUID = 1L;

    private Long id;

    private Date createdAt;

    private Date lastModifiedAt;

    private List<String> people;

    private List<PeopleKnowRelationDTO> relations;

    private List<String> celebrities;

    public ProblemSolutionResponseDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getLastModifiedAt() {
        return lastModifiedAt;
    }

    public void setLastModifiedAt(Date lastModifiedAt) {
        this.lastModifiedAt = lastModifiedAt;
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

    public List<String> getCelebrities() {
        return celebrities;
    }

    public void setCelebrities(List<String> celebrities) {
        this.celebrities = celebrities;
    }
}
