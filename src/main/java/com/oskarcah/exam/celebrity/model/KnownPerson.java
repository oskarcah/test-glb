package com.oskarcah.exam.celebrity.model;

import javax.persistence.*;

@Entity
@Table(name = "known_person")
public class KnownPerson {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    Long id;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name="person1_id")
    Person person1;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name="person2_id")
    Person person2;

    public KnownPerson() {
        super();
    }

    public KnownPerson(Person person1, Person person2) {
        this.person1 = person1;
        this.person2 = person2;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Person getPerson1() {
        return person1;
    }

    public void setPerson1(Person person1) {
        this.person1 = person1;
    }

    public Person getPerson2() {
        return person2;
    }

    public void setPerson2(Person person2) {
        this.person2 = person2;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("KnownPerson{");
        sb.append("id=").append(id);
        sb.append(", person1=").append(person1);
        sb.append(", person2=").append(person2);
        sb.append('}');
        return sb.toString();
    }
}
