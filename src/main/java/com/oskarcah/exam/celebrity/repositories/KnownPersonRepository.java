package com.oskarcah.exam.celebrity.repositories;

import com.oskarcah.exam.celebrity.model.KnownPerson;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface KnownPersonRepository extends CrudRepository<KnownPerson, Long> {

    @Modifying
    @Query("delete from KnownPerson k WHERE k.id IN ?1")
    public void deleteByIdIn(List<Long> ids);
}
