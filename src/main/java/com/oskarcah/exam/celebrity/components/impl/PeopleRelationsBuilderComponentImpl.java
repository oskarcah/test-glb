package com.oskarcah.exam.celebrity.components.impl;

import com.oskarcah.exam.celebrity.components.PeopleRelationsBuilderComponent;
import com.oskarcah.exam.celebrity.dto.PeopleKnowRelationDTO;
import com.oskarcah.exam.celebrity.model.KnownPerson;
import com.oskarcah.exam.celebrity.model.Person;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class PeopleRelationsBuilderComponentImpl implements PeopleRelationsBuilderComponent {

    @Override
    public List<KnownPerson> buildKnowPeopleRelations(List<PeopleKnowRelationDTO> relations, Map<String, Person> peopleMap) {
        return relations == null ? null : relations.stream()
                .map(r -> new KnownPerson(peopleMap.get(r.getX()), peopleMap.get(r.getY())))
                .collect(Collectors.toList());

    }
}
