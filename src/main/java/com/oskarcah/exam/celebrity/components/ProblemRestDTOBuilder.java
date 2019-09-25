package com.oskarcah.exam.celebrity.components;

import com.oskarcah.exam.celebrity.api.CelebrityApiController;
import com.oskarcah.exam.celebrity.dto.PeopleKnowRelationDTO;
import com.oskarcah.exam.celebrity.model.Problem;
import com.oskarcah.exam.celebrity.dto.ProblemSolutionResponseDTO;
import com.oskarcah.exam.celebrity.model.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Component with function of build instances of DTO used as response in API operations
 *
 */
@Component
public class ProblemRestDTOBuilder {

    private static final Logger LOGGER = LoggerFactory.getLogger(CelebrityApiController.class);

    public ProblemRestDTOBuilder() {

    }

    /**
     * Creates an instance of ProblemSolutionResponseDTO from Problem entity instance
     * @param problem Instance of Problem with data from Database
     * @return new instance of DTO with representantion of the problem and its solution
     */
    public ProblemSolutionResponseDTO createProblemSolutionResponseDTO(Problem problem) {
        ProblemSolutionResponseDTO result = new ProblemSolutionResponseDTO();
        result.setId(problem.getId());
        result.setCreatedAt(problem.getCreatedAt());
        result.setLastModifiedAt(problem.getLastModifiedAt());
        result.setPeople(problem.getPeople().stream().map(Person::getFullName).collect(Collectors.toList()));
        result.setRelations(problem.getPeopleRelations().stream().map(r -> new PeopleKnowRelationDTO(r.getPerson1().getFullName(), r.getPerson2().getFullName())).collect(Collectors.toList()));
        result.setCelebrities(problem.getCelebrities().stream().map(Person::getFullName).collect(Collectors.toList()));
        return result;
    }


    /**
     * Creates an instance of ProblemSolutionResponseDTO from Problem entity instance and takes care of null values
     * @param problem Instance of Problem with data from Database
     * @return new instance of DTO with representantion of the problem and its solution
     */
    public ProblemSolutionResponseDTO buildResponseDTO(Problem problem) {
        return problem == null ? null : createProblemSolutionResponseDTO(problem);
    }

    /**
     * Creates a List of roblemSolutionResponseDTO from a collection of Problem entities. It takes care of null values
     * @param problems List of  Instance of Problem with data from Database
     * @return new list with instances of DTOs with
     */
    public List<ProblemSolutionResponseDTO> buildListResponseDTO(Iterable<Problem> problems) {
        return problems == null ? new ArrayList<>() :
                StreamSupport.stream(problems.spliterator(), false)
                        .map(this::buildResponseDTO)
                        .collect(Collectors.toList());
    }


}
