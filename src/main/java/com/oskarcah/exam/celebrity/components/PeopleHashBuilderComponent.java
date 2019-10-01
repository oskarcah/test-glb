package com.oskarcah.exam.celebrity.components;

import com.oskarcah.exam.celebrity.model.Person;
import com.oskarcah.exam.celebrity.model.Problem;

import java.util.List;
import java.util.Map;

public interface PeopleHashBuilderComponent {

    List<Person> buildPeopleList(final List<String> peopleNames, final Problem problem);

    Map<String, Person> buildPeopleMapByName(final List<Person> people);
}
