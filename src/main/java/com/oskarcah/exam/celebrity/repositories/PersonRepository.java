package com.oskarcah.exam.celebrity.repositories;

import com.oskarcah.exam.celebrity.model.Person;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PersonRepository extends CrudRepository<Person, Long> {
    List<Person> findAllByProblemId(Long id);

    List<Person> findAllByProblemIdOrderByIndexAsc(Long id);

    void deleteByProblemId(Long id);

    @Modifying
    @Query("delete from Person p WHERE p.id IN ?1")
    public void deleteByIdIn(List<Long> ids);

}
