package com.oskarcah.exam.celebrity.components;

import com.oskarcah.exam.celebrity.dto.PeopleKnowRelationDTO;
import com.oskarcah.exam.celebrity.model.KnownPerson;
import com.oskarcah.exam.celebrity.model.Person;

import java.util.List;
import java.util.Map;

public interface PeopleRelationsBuilderComponent {
    List<KnownPerson> buildKnowPeopleRelations(final List<PeopleKnowRelationDTO> relations, final Map<String, Person> peopleMap);
}
