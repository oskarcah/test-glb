package com.oskarcah.exam.celebrity.components;

import com.oskarcah.exam.celebrity.dto.ProblemSolutionResponseDTO;
import com.oskarcah.exam.celebrity.model.Problem;

import java.util.List;

public interface ProblemRestDTOBuilder {
    /**
     * Creates an instance of ProblemSolutionResponseDTO from Problem entity instance
     * @param problem Instance of Problem with data from Database
     * @return new instance of DTO with representantion of the problem and its solution
     */
    ProblemSolutionResponseDTO createProblemSolutionResponseDTO(Problem problem);

    /**
     * Creates an instance of ProblemSolutionResponseDTO from Problem entity instance and takes care of null values
     * @param problem Instance of Problem with data from Database
     * @return new instance of DTO with representantion of the problem and its solution
     */
    ProblemSolutionResponseDTO buildResponseDTO(Problem problem);

    /**
     * Creates a List of roblemSolutionResponseDTO from a collection of Problem entities. It takes care of null values
     * @param problems List of  Instance of Problem with data from Database
     * @return new list with instances of DTOs with
     */
    List<ProblemSolutionResponseDTO> buildListResponseDTO(Iterable<Problem> problems);
}
