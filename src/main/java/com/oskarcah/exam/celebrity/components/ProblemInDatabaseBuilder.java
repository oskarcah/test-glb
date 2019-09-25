package com.oskarcah.exam.celebrity.components;

import com.oskarcah.exam.celebrity.api.CelebrityApiController;
import com.oskarcah.exam.celebrity.dto.ProblemSetRequestDTO;
import com.oskarcah.exam.celebrity.model.KnownPerson;
import com.oskarcah.exam.celebrity.model.Person;
import com.oskarcah.exam.celebrity.model.Problem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
public class ProblemInDatabaseBuilder {

    private static final Logger LOGGER = LoggerFactory.getLogger(CelebrityApiController.class);
    @Autowired
    ProblemSolver problemSolver;
    @Autowired
    private DataAccessComponent dataAccess;

    public ProblemInDatabaseBuilder() {
        super();
    }

    /**
     * Created or Updates and saves a record of Problem table with data provided by DTO. During the updating, the problem is solved and the solution updated as well
     *
     * @param problem Instance of Problem retrieved from dataase
     * @param request Request data for modification.
     */
    void updateProblemInstance(final Problem problem, ProblemSetRequestDTO request) {
        // create people
        final int[] index = {0};
        final List<Person> dataBasePeople = request.getPeople().stream()
                .map(name -> new Person(name, index[0]++, problem))
                .collect(Collectors.toList());

        // map for temporal caching people indexed by name
        final Map<String, Person> peopleHash = dataBasePeople.stream().collect(Collectors.toMap(Person::getFullName, p -> p));

        // create relations
        final List<KnownPerson> knownPeopleRelations = request.getRelations().stream()
                .map(r -> new KnownPerson(peopleHash.get(r.getX()), peopleHash.get(r.getY()))).collect(Collectors.toList());

        problem.setPeople(dataBasePeople);
        problem.setPeopleRelations(knownPeopleRelations);

        // solve problem
        List<Person> solution = problemSolver.solveProblem(problem);
        problem.setCelebrities(solution);
        problem.setLastModifiedAt(Calendar.getInstance().getTime());
        dataAccess.saveProblem(problem);
    }


    /**
     * Uodate Problem Record in Database.
     *
     * @param problem Instance of Problem retrieved from dataase
     * @param request Request data for modification.
     * @return the same instance given by problem parameter
     */
    public Problem updateProblemSet(Problem problem, ProblemSetRequestDTO request) {
        if (problem != null) {
            // delete current people and related lists
            List<Long> p1 = problem.getPeople().stream().map(Person::getId).collect(Collectors.toList());
            List<Long> p2 = problem.getCelebrities().stream().map(Person::getId).collect(Collectors.toList());
            p1.addAll(p2);
            List<Long> kp = problem.getPeopleRelations().stream().map(KnownPerson::getId).collect(Collectors.toList());

            problem.getPeople().clear();
            problem.getCelebrities().clear();
            problem.getPeopleRelations().clear();
            dataAccess.deletePersonRelationsByIds(kp);
            dataAccess.deletePeopleByIds(p1);

            // update problem
            updateProblemInstance(problem, request);
        }
        return problem;
    }

    /**
     *  Creates a new record of Problem with data given by request DTO
     * @param request  Data request for creation
     * @return the new instance of Problem already saved in database
     */
    public Problem buildProblemSet(ProblemSetRequestDTO request) {
        if (request == null) {
            return null;
        }
        Problem problem = new Problem();
        problem.setCreatedAt(Calendar.getInstance().getTime());
        updateProblemInstance(problem, request);
        return problem;
    }
}
