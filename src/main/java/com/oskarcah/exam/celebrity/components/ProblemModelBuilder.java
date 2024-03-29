package com.oskarcah.exam.celebrity.components;

import com.oskarcah.exam.celebrity.dto.ProblemSetRequestDTO;
import com.oskarcah.exam.celebrity.model.Problem;

/**
 *
 */
public interface ProblemModelBuilder {

    /**
     * Creates a new record of Problem with data given by request DTO
     *
     * @param request Data request for creation
     * @return the new instance of Problem already saved in database
     */
    Problem buildProblemSet(final ProblemSetRequestDTO request);

    /**
     * @param currentProblem
     * @param request
     * @return
     */
    Problem buildProblemSet(final Problem currentProblem, final ProblemSetRequestDTO request);


    /**
     * Created or Updates and saves a record of Problem table with data provided by DTO. During the updating, the problem is solved and the solution updated as well
     *
     * @param problem Instance of Problem retrieved from dataase
     * @param request Request data for modification.
     */
    void updateProblemInstance(final Problem problem, final ProblemSetRequestDTO request);
}
