package com.oskarcah.exam.celebrity.components.impl;

import com.oskarcah.exam.celebrity.components.ProblemValidatorComponent;
import com.oskarcah.exam.celebrity.dto.ProblemSetRequestDTO;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class ProblemValidatorComponentImpl implements ProblemValidatorComponent {

    @Override
    public boolean validProblemSize(final ProblemSetRequestDTO problem) {
        return problem != null && problem.getPeople() != null && problem.getPeople().size() > 2;
    }

    @Override
    public List<String> repeatedPeopleNames(final ProblemSetRequestDTO problem) {
        if (problem != null && problem.getPeople() != null) {
            final List<String> people = problem.getPeople();
            final Map<String, Integer> frequencyByName = new HashMap<>();
            // iterate over people and update frequencies map
            people.stream().forEach(name -> {
                Integer n = frequencyByName.get(name);
                if (n == null) {
                    frequencyByName.put(name, 1);
                } else {
                    Integer m = frequencyByName.put(name, n + 1);
                }
            });
            return people.stream()
                    .filter(name -> {
                        return frequencyByName.get(name) > 1;
                    })
                    .collect(Collectors.toList());
        } else {
            return new ArrayList<>();
        }
    }

    @Override
    public boolean validateNotEmptyNames(ProblemSetRequestDTO problem) {
        return (problem == null || problem.getPeople() == null) ? false :
                (problem.getPeople()
                        .stream()
                        .filter(p -> {
                            return p == null || p.trim().isEmpty();
                        })
                        .count() == 0);
    }

    @Override
    public List<String> notExistingPeopleInRelations(final ProblemSetRequestDTO problem) {
        if (problem != null && problem.getPeople() != null && problem.getRelations() != null) {
            final List<String> people = problem.getPeople();
            final HashMap<String, Boolean> mapNotInPeople = new LinkedHashMap<>();
            problem.getRelations().stream()
                    .forEach(r -> {
                        if (!people.contains(r.getX())) {
                            mapNotInPeople.put(r.getX(), true);
                        }
                        if (!people.contains(r.getY())) {
                            mapNotInPeople.put(r.getY(), true);
                        }
                    });
            return mapNotInPeople.keySet().stream().collect(Collectors.toList());
        } else {
            return new ArrayList<>();
        }
    }

    @Override
    public boolean validateNotEmptyRelations(final ProblemSetRequestDTO problem) {
        return (problem == null || problem.getRelations() == null) ? false :
                problem.getRelations().stream()
                        .filter(r -> r.getX() == null || r.getY() == null || r.getX().trim().isEmpty() || r.getY().trim().isEmpty())
                        .count() == 0;
    }
}
