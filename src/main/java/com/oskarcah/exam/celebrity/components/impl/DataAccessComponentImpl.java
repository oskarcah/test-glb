package com.oskarcah.exam.celebrity.components.impl;

import com.oskarcah.exam.celebrity.application.CelebrityApplication;
import com.oskarcah.exam.celebrity.components.DataAccessComponent;
import com.oskarcah.exam.celebrity.exceptions.DataAccessException;
import com.oskarcah.exam.celebrity.exceptions.ProblemNotFoundException;
import com.oskarcah.exam.celebrity.model.Problem;
import com.oskarcah.exam.celebrity.repositories.KnownPersonRepository;
import com.oskarcah.exam.celebrity.repositories.PersonRepository;
import com.oskarcah.exam.celebrity.repositories.ProblemRepository;
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
    public Iterable<Problem> findAllProblems() throws Exception {
        try {
            Iterable<Problem> problems = problemRepository.findAll();
            if (!problems.iterator().hasNext()) {
                throw new ProblemNotFoundException();
            }
            return problems;
        } catch (Exception e) {
            throw new DataAccessException(e);
        }
    }

    @Override
    public Optional<Problem> findProblemById(Long id) throws Exception {
        try {
            CelebrityApplication.LOGGER.info("[DataAccessComponentImpl.findProblemById] - method begin id=" + id);
            Optional<Problem> opt = problemRepository.findById(id);
            CelebrityApplication.LOGGER.info("[DataAccessComponentImpl.findProblemById] - method end id=" + id);
            return opt;
        } catch (Exception e) {
            CelebrityApplication.LOGGER.info("[DataAccessComponentImpl.findProblemById] - Exception thrown " + e);
            throw new DataAccessException(e);
        }
    }

    @Override
    public boolean existsProblemById(Long id) throws Exception {
        try {
            CelebrityApplication.LOGGER.info("[DataAccessComponentImpl.existsProblemById] - method begin id=" + id);
            return problemRepository.existsById(id);
        } catch (Exception e) {
            CelebrityApplication.LOGGER.info("[DataAccessComponentImpl.existsProblemById] - Exception thrown " + e);
            throw new DataAccessException(e);
        }
    }

    @Override
    public void deleteProblemById(Long id) throws Exception {
        try {
            CelebrityApplication.LOGGER.info("[DataAccessComponentImpl.deleteProblemById] - method begin id=" + id);
            problemRepository.deleteById(id);
        } catch (Exception e) {
            CelebrityApplication.LOGGER.info("[DataAccessComponentImpl.deleteProblemById] - Exception thrown " + e);
            throw new DataAccessException(e);
        }
    }

    @Override
    public Problem saveProblem(Problem p) throws Exception {
        try {
            CelebrityApplication.LOGGER.info("[DataAccessComponentImpl.saveProblem] - method begin problem=" + p);
            return problemRepository.save(p);
        } catch (Exception e) {
            CelebrityApplication.LOGGER.info("[DataAccessComponentImpl.saveProblem] - Exception thrown " + e);
            throw new DataAccessException(e);
        }
    }

    @Override
    public void deletePersonById(Long id) throws Exception {
        try {
            CelebrityApplication.LOGGER.info("[DataAccessComponentImpl.deletePersonById] - method begin id=" + id);
            personRepository.deleteById(id);
        } catch (Exception e) {
            CelebrityApplication.LOGGER.info("[DataAccessComponentImpl.deletePersonById] - Exception thrown " + e);
            throw new DataAccessException(e);
        }
    }

    @Override
    public void deletePeopleByIds(List<Long> ids) throws Exception {
        try {
            CelebrityApplication.LOGGER.info("[DataAccessComponentImpl.deletePeopleByIds] - method begin ids=" + ids);
            personRepository.deleteByIdIn(ids);
        } catch (Exception e) {
            CelebrityApplication.LOGGER.info("[DataAccessComponentImpl.deletePeopleByIds] - Exception thrown " + e);
            throw new DataAccessException(e);
        }
    }

    @Override
    public void deletePersonRelationById(Long id) throws Exception {
        try {
            CelebrityApplication.LOGGER.info("[DataAccessComponentImpl.deletePersonRelationById] - method begin id=" + id);
            knownPersonRepository.deleteById(id);
        } catch (Exception e) {
            CelebrityApplication.LOGGER.info("[DataAccessComponentImpl.deletePersonRelationById] - Exception thrown " + e);
            throw new DataAccessException(e);
        }
    }

    @Override
    public void deletePersonRelationsByIds(List<Long> ids) throws Exception {
        try {
            knownPersonRepository.deleteByIdIn(ids);
        } catch (Exception e) {
            throw new DataAccessException(e);
        }
    }
}
