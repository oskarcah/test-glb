package com.oskarcah.exam.celebrity.model;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity()
@Table(name="problem_set")
public class Problem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="created_at")
    private Date createdAt;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="last_modified_at")
    private Date lastModifiedAt;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name="problem_set_id")
    private List<KnownPerson> peopleRelations;

    @OneToMany(cascade =  CascadeType.ALL)
    private List<Person> people;

    @OneToMany(cascade =  CascadeType.ALL)
    private List<Person> celebrities;

    public Problem() {

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

    public List<KnownPerson> getPeopleRelations() {
        return peopleRelations;
    }

    public void setPeopleRelations(List<KnownPerson> peopleRelations) {
        this.peopleRelations = peopleRelations;
    }

    public List<Person> getCelebrities() {
        return celebrities;
    }

    public void setCelebrities(List<Person> celebrities) {
        this.celebrities = celebrities;
    }

    public List<Person> getPeople() {
        return people;
    }

    public void setPeople(List<Person> people) {
        this.people = people;
    }

    @Transient
    public Integer getPeopleCount() {
        return people == null ? 0 : people.size();
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("ProblemSet{");
        sb.append("id=").append(id);
        sb.append(", createdAt=").append(createdAt);
        sb.append(", lastModifiedAt=").append(lastModifiedAt);
        sb.append(", peopleRelations=").append(peopleRelations);
        sb.append(", people=").append(people);
        sb.append(", celebrities=").append(celebrities);
        sb.append('}');
        return sb.toString();
    }
}
