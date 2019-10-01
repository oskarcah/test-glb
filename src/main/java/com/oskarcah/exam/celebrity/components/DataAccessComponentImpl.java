package com.oskarcah.exam.celebrity.components;

import com.oskarcah.exam.celebrity.api.CelebrityApiController;
import com.oskarcah.exam.celebrity.application.CelebrityApplication;
import com.oskarcah.exam.celebrity.model.Problem;
import com.oskarcah.exam.celebrity.repositories.ProblemRepository;
import com.oskarcah.exam.celebrity.repositories.KnownPersonRepository;
import com.oskarcah.exam.celebrity.repositories.PersonRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

/**
 * Component that provides the database access layer with operation for retrieve, save, update and delete data.
 */
@Component
public class DataAccessComponentImpl implements DataAccessComponent {

    private ProblemRepository problemRepository;

    private KnownPersonRepository knownPersonRepository;

    private PersonRepository personRepository;

    @Autowired
    public DataAccessComponentImpl(ProblemRepository problemRepository, KnownPersonRepository knownPersonRepository, PersonRepository personRepository) {
        this.problemRepository = problemRepository;
        this.knownPersonRepository = knownPersonRepository;
        this.personRepository = personRepository;
    }

    @Override
    public Iterable<Problem> findAllProblems() {
        return problemRepository.findAll();
    }

    @Override
    public Problem findProblemById(Long id) {
        CelebrityApplication.LOGGER.info("[DataAccessComponentImpl.findProblemById] - method begin id=" + id);
        Optional<Problem> opt = problemRepository.findById(id);
        CelebrityApplication.LOGGER.info("[DataAccessComponentImpl.findProblemById] - method end id=" + id);
        return opt.isPresent() ? opt.get() : null;
    }

    @Override
    public boolean existsProblemById(Long id) {
        return problemRepository.existsById(id);
    }

    @Override
    public void deleteProblemById(Long id) {
        problemRepository.deleteById(id);
    }

    @Override
    public void saveProblem(Problem p) {
        problemRepository.save(p);
    }

    @Override
    public void deletePersonById(Long id) {
        personRepository.deleteById(id);
    }

    @Override
    public void deletePeopleByIds(List<Long> ids) {
        personRepository.deleteByIdIn(ids);
    }

    @Override
    public void deletePersonRelationById(Long id) {
        knownPersonRepository.deleteById(id);
    }

    @Override
    public void deletePersonRelationsByIds(List<Long> ids) {
        knownPersonRepository.deleteByIdIn(ids);
    }
}
