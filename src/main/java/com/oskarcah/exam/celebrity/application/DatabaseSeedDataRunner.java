package com.oskarcah.exam.celebrity.application;

import com.oskarcah.exam.celebrity.components.ProblemSolver;
import com.oskarcah.exam.celebrity.components.ProblemSolverImpl;
import com.oskarcah.exam.celebrity.model.KnownPerson;
import com.oskarcah.exam.celebrity.model.Person;
import com.oskarcah.exam.celebrity.model.Problem;
import com.oskarcah.exam.celebrity.repositories.KnownPersonRepository;
import com.oskarcah.exam.celebrity.repositories.PersonRepository;
import com.oskarcah.exam.celebrity.repositories.ProblemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Runner for initial application seeding and config.
 * <p>
 * In the example a  model problem is created and save into database.
 */
@Component
@Profile("!test")
public class DatabaseSeedDataRunner implements CommandLineRunner {

    KnownPersonRepository knownPersonRepository;

    ProblemRepository problemRepository;

    PersonRepository personRepository;

    ProblemSolver solver;

    @Autowired
    public DatabaseSeedDataRunner(KnownPersonRepository knownPersonRepository, ProblemRepository problemRepository, PersonRepository personRepository, ProblemSolver solver) {
        this.knownPersonRepository = knownPersonRepository;
        this.problemRepository = problemRepository;
        this.personRepository = personRepository;
        this.solver = solver;
    }

    @Override
    public void run(String... args) throws Exception {

        CelebrityApplication.LOGGER.info("Adding one test case in DB");

        /*
         *  Seeding the database
         *
         * initial test problem
         * */

        // problem set 1
        Problem problem1 = new Problem();
        Date createdAt = Calendar.getInstance().getTime();
        problem1.setCreatedAt(createdAt);
        problem1.setLastModifiedAt(createdAt);

        // people problem
        List<Person> people = new ArrayList<>();
        problem1.setPeople(people);
        people.add(new Person("Jorge", 0, problem1));
        people.add(new Person("Carlos", 1, problem1));
        people.add(new Person("Juan", 2, problem1));
        people.add(new Person("Maria", 3, problem1));
        people.add(new Person("Ana", 4, problem1));

        // people relations
        List<KnownPerson> relations = new ArrayList<>();
        problem1.setPeopleRelations(relations);
        relations.add(new KnownPerson(people.get(0), people.get(1)));
        relations.add(new KnownPerson(people.get(0), people.get(3)));
        relations.add(new KnownPerson(people.get(0), people.get(2)));
        relations.add(new KnownPerson(people.get(1), people.get(4)));
        relations.add(new KnownPerson(people.get(1), people.get(3)));
        relations.add(new KnownPerson(people.get(2), people.get(3)));
        relations.add(new KnownPerson(people.get(4), people.get(1)));
        relations.add(new KnownPerson(people.get(4), people.get(2)));
        relations.add(new KnownPerson(people.get(4), people.get(3)));
        problem1.setCelebrities(solver.solveProblem(problem1));
        problemRepository.save(problem1);

        // problem set 2
        problem1 = new Problem();
        createdAt = Calendar.getInstance().getTime();
        problem1.setCreatedAt(createdAt);
        problem1.setLastModifiedAt(createdAt);

        // people problem
        people = new ArrayList<>();
        problem1.setPeople(people);
        people.add(new Person("Joe", 0, problem1));
        people.add(new Person("Peter", 1, problem1));
        people.add(new Person("Stephen", 2, problem1));
        people.add(new Person("Angela", 3, problem1));
        people.add(new Person("Myra", 4, problem1));
        people.add(new Person("Frank", 5, problem1));
        people.add(new Person("Lenka", 6, problem1));

        // people relations
        relations = new ArrayList<>();
        problem1.setPeopleRelations(relations);
        relations.add(new KnownPerson(people.get(0), people.get(1)));
        relations.add(new KnownPerson(people.get(0), people.get(2)));
        relations.add(new KnownPerson(people.get(0), people.get(4)));
        relations.add(new KnownPerson(people.get(0), people.get(5)));
        relations.add(new KnownPerson(people.get(0), people.get(6)));

        relations.add(new KnownPerson(people.get(1), people.get(3)));
        relations.add(new KnownPerson(people.get(1), people.get(5)));

        relations.add(new KnownPerson(people.get(2), people.get(4)));
        relations.add(new KnownPerson(people.get(2), people.get(6)));
        relations.add(new KnownPerson(people.get(2), people.get(5)));

        relations.add(new KnownPerson(people.get(3), people.get(6)));
        relations.add(new KnownPerson(people.get(3), people.get(0)));
        relations.add(new KnownPerson(people.get(2), people.get(1)));
        relations.add(new KnownPerson(people.get(2), people.get(5)));

        relations.add(new KnownPerson(people.get(4), people.get(5)));
        relations.add(new KnownPerson(people.get(6), people.get(5)));


        problem1.setCelebrities(solver.solveProblem(problem1));
        problemRepository.save(problem1);


        CelebrityApplication.LOGGER.info("celebrities" + problem1.getCelebrities());
        CelebrityApplication.LOGGER.info("Adding one test case in DB");
        CelebrityApplication.LOGGER.info("Preloaded problem with id=" + problem1.getId());
        CelebrityApplication.LOGGER.info("Preloaded problem=" + problem1.toString());

    }
}
