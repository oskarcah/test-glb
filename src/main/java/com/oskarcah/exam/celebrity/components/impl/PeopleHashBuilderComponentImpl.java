package com.oskarcah.exam.celebrity.components.impl;

import com.oskarcah.exam.celebrity.components.PeopleHashBuilderComponent;
import com.oskarcah.exam.celebrity.model.Person;
import com.oskarcah.exam.celebrity.model.Problem;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class PeopleHashBuilderComponentImpl implements PeopleHashBuilderComponent {
    @Override
    public List<Person> buildPeopleList(final List<String> peopleNames, final Problem problem) {
        final int[] index = {0};
        return peopleNames == null ? null : peopleNames.stream()
                .map(name -> new Person(name, index[0]++, problem))
                .collect(Collectors.toList());
    }

    @Override
    public Map<String, Person> buildPeopleMapByName(final List<Person> people) {
        return people == null ? null : people.stream()
                .collect(Collectors.toMap(Person::getFullName, p -> p));
    }
}
