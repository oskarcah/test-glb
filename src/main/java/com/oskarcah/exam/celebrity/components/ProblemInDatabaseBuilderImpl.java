package com.oskarcah.exam.celebrity.components;

import com.oskarcah.exam.celebrity.application.CelebrityApplication;
import com.oskarcah.exam.celebrity.dto.ProblemSetRequestDTO;
import com.oskarcah.exam.celebrity.model.KnownPerson;
import com.oskarcah.exam.celebrity.model.Person;
import com.oskarcah.exam.celebrity.model.Problem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Component with function of transform data between database entities and DTO used by RESTful API
 * and create/update in database.
 */
@Component
public class ProblemInDatabaseBuilderImpl implements ProblemInDatabaseBuilder {

    @Autowired
    ProblemSolverImpl problemSolver;
    @Autowired
    private DataAccessComponentImpl dataAccess;

    public ProblemInDatabaseBuilderImpl() {
        super();
    }

    /**
     * Created or Updates and saves a record of Problem table with data provided by DTO. During the updating, the problem is solved and the solution updated as well
     *
     * @param problem Instance of Problem retrieved from dataase
     * @param request Request data for modification.
     */
    public void updateProblemInstance(final Problem problem, ProblemSetRequestDTO request) {
        CelebrityApplication.LOGGER.info("[ProblemInDatabaseBuilderImpl.updateProblemInstance] - method begin problem.id=" + problem.getId() + " request=" + request);

        // create people
        final int[] index = {0};
        final List<Person> dataBasePeople = request.getPeople().stream()
                .map(name -> new Person(name, index[0]++, problem))
                .collect(Collectors.toList());

        // map for temporal caching people indexed by name
        final Map<String, Person> peopleHash = dataBasePeople.stream().collect(Collectors.toMap(Person::getFullName, p -> p));
        CelebrityApplication.LOGGER.info("[ProblemInDatabaseBuilderImpl.updateProblemInstance] - created people  hash by id");

        // create relations
        final List<KnownPerson> knownPeopleRelations = request.getRelations().stream()
                .map(r -> new KnownPerson(peopleHash.get(r.getX()), peopleHash.get(r.getY()))).collect(Collectors.toList());
        CelebrityApplication.LOGGER.info("[ProblemInDatabaseBuilderImpl.updateProblemInstance] - created people relations hash");

        problem.setPeople(dataBasePeople);
        problem.setPeopleRelations(knownPeopleRelations);


        // solve problem
        List<Person> solution = problemSolver.solveProblem(problem);
        problem.setCelebrities(solution);
        problem.setLastModifiedAt(Calendar.getInstance().getTime());
        dataAccess.saveProblem(problem);
        CelebrityApplication.LOGGER.info("[ProblemInDatabaseBuilderImpl.updateProblemInstance] - method end problem.id=" + problem.getId());

    }


    @Override
    public Problem updateProblemSet(Problem problem, ProblemSetRequestDTO request) {
        CelebrityApplication.LOGGER.info("[ProblemInDatabaseBuilderImpl.updateProblemSet] - method begin problem.id=" + problem.getId() + " request=" + request);
        if (problem != null) {
            // delete current people and related lists
            List<Long> p1 = problem.getPeople().stream().map(Person::getId).collect(Collectors.toList());
            List<Long> p2 = problem.getCelebrities().stream().map(Person::getId).collect(Collectors.toList());
            p1.addAll(p2);
            List<Long> kp = problem.getPeopleRelations().stream().map(KnownPerson::getId).collect(Collectors.toList());

            problem.getPeople().clear();
            problem.getCelebrities().clear();
            problem.getPeopleRelations().clear();

            CelebrityApplication.LOGGER.info("[ProblemInDatabaseBuilderImpl.updateProblemSet] - delete old people relations");
            dataAccess.deletePersonRelationsByIds(kp);
            CelebrityApplication.LOGGER.info("[ProblemInDatabaseBuilderImpl.updateProblemSet] - delete old people list");
            dataAccess.deletePeopleByIds(p1);

            // update problem
            updateProblemInstance(problem, request);
        }
        CelebrityApplication.LOGGER.info("[ProblemInDatabaseBuilderImpl.updateProblemSet] - method end return problem.id=" + problem.getId());
        return problem;
    }

    @Override
    public Problem buildProblemSet(ProblemSetRequestDTO request) {
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
}
