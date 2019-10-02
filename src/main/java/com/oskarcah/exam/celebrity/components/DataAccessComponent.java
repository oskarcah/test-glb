package com.oskarcah.exam.celebrity.components;

import com.oskarcah.exam.celebrity.model.Problem;

import java.util.List;
import java.util.Optional;

/**
 *
 */
public interface DataAccessComponent {
    /**
     * Query for all Problem registers in database
     *
     * @return List of all registers
     */
    Iterable<Problem> findAllProblems() throws Exception;

    /**
     * Query for retrieving Problem register with specified id
     *
     * @param id value to search as primary key
     * @return Problem record with specified id or null if there is not any record.
     */
    Optional<Problem> findProblemById(Long id) throws Exception;

    /**
     * Query for testing if exist a Problem record by id
     *
     * @param id value to search as primary key
     * @return true or false.
     */
    boolean existsProblemById(Long id) throws Exception;

    /**
     * Query for deleting Problem register with specified id
     *
     * @param id value to search as primary key
     */
    void deleteProblemById(Long id) throws Exception;

    /**
     * @param p
     * @return
     */
    Problem saveProblem(Problem p) throws Exception;

    /**
     * Query for deleting Person record with specified id
     *
     * @param id value to search as primary key
     */
    void deletePersonById(Long id) throws Exception;

    /**
     * Query for deleting one or more Person records which id is in specified list
     *
     * @param ids list of ids of records to be deleted
     */
    void deletePeopleByIds(List<Long> ids) throws Exception;

    /**
     * Query for deleting KnownPerson register with specified id
     *
     * @param id value to search as primary key
     */
    void deletePersonRelationById(Long id) throws Exception;

    /**
     * Query for deleting one or more Person records which id is in specified list
     *
     * @param ids list of ids of records to be deleted
     */
    void deletePersonRelationsByIds(List<Long> ids) throws Exception;
}
