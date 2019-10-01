package com.oskarcah.exam.celebrity.components;

import com.oskarcah.exam.celebrity.model.Problem;

import java.util.List;

public interface DataAccessComponent {
    /**
     * Query for all Problem registers in database
     *
     * @return List of all registers
     */
    Iterable<Problem> findAllProblems();

    /**
     *
     * Query for retrieving Problem register with specified id
     *
     * @param id value to search as primary key
     * @return Problem record with specified id or null if there is not any record.
     */
    Problem findProblemById(Long id);

    /**
     *
     * Query for testing if exist a Problem record by id
     *
     * @param id value to search as primary key
     * @return true or false.
     */
    boolean existsProblemById(Long id);

    /**
     *
     * Query for deleting Problem register with specified id
     *
     * @param id value to search as primary key
     */
    void deleteProblemById(Long id);

    /**
     *
     * Query for inserting Problem register
     *
     * @param p instance of Problem to be inserted
     */
    void saveProblem(Problem p);

    /**
     *
     * Query for deleting Person record with specified id
     *
     * @param id value to search as primary key
     */
    void deletePersonById(Long id);

    /**
     *
     * Query for deleting one or more Person records which id is in specified list
     *
     * @param ids list of ids of records to be deleted
     */
    void deletePeopleByIds(List<Long> ids);

    /**
     *
     * Query for deleting KnownPerson register with specified id
     *
     * @param id value to search as primary key
     */
    void deletePersonRelationById(Long id);

    /**
     *
     * Query for deleting one or more Person records which id is in specified list
     *
     * @param ids list of ids of records to be deleted
     */
    void deletePersonRelationsByIds(List<Long> ids);
}
