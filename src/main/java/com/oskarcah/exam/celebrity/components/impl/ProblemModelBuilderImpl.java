package com.oskarcah.exam.celebrity.components.impl;

import com.oskarcah.exam.celebrity.application.CelebrityApplication;
import com.oskarcah.exam.celebrity.components.PeopleHashBuilderComponent;
import com.oskarcah.exam.celebrity.components.PeopleRelationsBuilderComponent;
import com.oskarcah.exam.celebrity.components.ProblemModelBuilder;
import com.oskarcah.exam.celebrity.dto.ProblemSetRequestDTO;
import com.oskarcah.exam.celebrity.model.Person;
import com.oskarcah.exam.celebrity.model.Problem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.List;
import java.util.Map;

/**
 * Component with function of transform data between database entities and DTO used by RESTful API
 * and create/update in database.
 */
@Component
public class ProblemModelBuilderImpl implements ProblemModelBuilder {

    private PeopleHashBuilderComponent hashBuilder;

    private PeopleRelationsBuilderComponent relationsBuilder;

    @Autowired
    public ProblemModelBuilderImpl(PeopleHashBuilderComponent hashBuilder, PeopleRelationsBuilderComponent relationsBuilder) {
        this.hashBuilder = hashBuilder;
        this.relationsBuilder = relationsBuilder;
    }

     @Override
    public void updateProblemInstance(final Problem problem, ProblemSetRequestDTO request) {
        CelebrityApplication.LOGGER.info("[ProblemInDatabaseBuilderImpl.updateProblemInstance] - method begin problem.id=" + problem.getId() + " request=" + request);

        List<Person> dataBasePeople = hashBuilder.buildPeopleList(request.getPeople(), problem);
        Map<String, Person> peopleHash = hashBuilder.buildPeopleMapByName(dataBasePeople);
        CelebrityApplication.LOGGER.info("[ProblemInDatabaseBuilderImpl.updateProblemInstance] - created people  hash by id");
        problem.setPeople(dataBasePeople);
        problem.setPeopleRelations(relationsBuilder.buildKnowPeopleRelations(request.getRelations(), peopleHash));
        CelebrityApplication.LOGGER.info("[ProblemInDatabaseBuilderImpl.updateProblemInstance] - method end problem.id=" + problem.getId());

    }

    @Override
    public Problem buildProblemSet(final ProblemSetRequestDTO request) {
        CelebrityApplication.LOGGER.info("[ProblemInDatabaseBuilderImpl.buildProblemSet] - method begin  request=" + request);
        if (request == null) {
            return null;
        }
        CelebrityApplication.LOGGER.info("[ProblemInDatabaseBuilderImpl.buildProblemSet] - creating new problem instance");
        Problem problem = new Problem();
        problem.setCreatedAt(Calendar.getInstance().getTime());
        updateProblemInstance(problem, request);
        CelebrityApplication.LOGGER.info("[ProblemInDatabaseBuilderImpl.buildProblemSet] - created new problem instance with id = " + problem.getId());
        return problem;
    }

    @Override
    public Problem buildProblemSet(final Problem currentProblem, final ProblemSetRequestDTO request) {
        updateProblemInstance(currentProblem, request);
        return currentProblem;
    }

}
