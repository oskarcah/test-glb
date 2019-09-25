package com.oskarcah.exam.celebrity.components;

import com.oskarcah.exam.celebrity.api.CelebrityApiController;
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
public class DataAccessComponent {

    private static final Logger LOGGER = LoggerFactory.getLogger(CelebrityApiController.class);

    @Autowired
    private ProblemRepository problemRepository;

    @Autowired
    private KnownPersonRepository knownPersonRepository;

    @Autowired
    private PersonRepository personRepository;


    public DataAccessComponent() {

    }

    /**
     * Query for all Problem registers in database
     *
     * @return List of all registers
     */
    public Iterable<Problem> findAllProblems() {
        return problemRepository.findAll();
    }

    /**
     *
     * Query for retrieving Problem register with specified id
     *
     * @param id value to search as primary key
     * @return Problem record with specified id or null if there is not any record.
     */
    public Problem findProblemById(Long id) {
        Optional<Problem> opt = problemRepository.findById(id);
        return opt.isPresent() ? opt.get() : null;
    }

    /**
     *
     * Query for testing if exist a Problem record by id
     *
     * @param id value to search as primary key
     * @return true or false.
     */
    public boolean existsProblemById(Long id) {
        return problemRepository.existsById(id);
    }

    /**
     *
     * Query for deleting Problem register with specified id
     *
     * @param id value to search as primary key
     */
    public void deleteProblemById(Long id) {
        problemRepository.deleteById(id);
    }

    /**
     *
     * Query for inserting Problem register
     *
     * @param p instance of Problem to be inserted
     */
    public void saveProblem(Problem p) {
        problemRepository.save(p);
    }

    /**
     *
     * Query for deleting Person record with specified id
     *
     * @param id value to search as primary key
     */
    public void deletePersonById(Long id) {
        personRepository.deleteById(id);
    }

    /**
     *
     * Query for deleting one or more Person records which id is in specified list
     *
     * @param ids list of ids of records to be deleted
     */
    public void deletePeopleByIds(List<Long> ids) {
        personRepository.deleteByIdIn(ids);
    }

    /**
     *
     * Query for deleting KnownPerson register with specified id
     *
     * @param id value to search as primary key
     */
    public void deletePersonRelationById(Long id) {
        knownPersonRepository.deleteById(id);
    }

    /**
     *
     * Query for deleting one or more Person records which id is in specified list
     *
     * @param ids list of ids of records to be deleted
     */
    public void deletePersonRelationsByIds(List<Long> ids) {
        knownPersonRepository.deleteByIdIn(ids);
    }
}
