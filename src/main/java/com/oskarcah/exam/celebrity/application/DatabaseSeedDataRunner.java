package com.oskarcah.exam.celebrity.application;

import com.oskarcah.exam.celebrity.components.ProblemSolverComponent;
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

    ProblemSolverComponent solver;

    @Autowired
    public DatabaseSeedDataRunner(KnownPersonRepository knownPersonRepository, ProblemRepository problemRepository, PersonRepository personRepository, ProblemSolverComponent solver) {
        this.knownPersonRepository = knownPersonRepository;
        this.problemRepository = problemRepository;
        this.personRepository = personRepository;
        this.solver = solver;
    }

    @Override
    public void run(String... args) throws Exception {

        CelebrityApplication.LOGGER.info("Adding one test case in DB");

    }
}
