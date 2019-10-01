package com.oskarcah.exam.celebrity.components;

import com.oskarcah.exam.celebrity.api.CelebrityApiController;
import com.oskarcah.exam.celebrity.application.CelebrityApplication;
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
public class ProblemRestDTOBuilderImpl implements ProblemRestDTOBuilder {


    public ProblemRestDTOBuilderImpl() {
        super();
    }

    @Override
    public ProblemSolutionResponseDTO createProblemSolutionResponseDTO(Problem problem) {
        CelebrityApplication.LOGGER.info("[ProblemRestDTOBuilderImpl.createProblemSolutionResponseDTO] - method begin  problem=" + problem);
        ProblemSolutionResponseDTO result = new ProblemSolutionResponseDTO();
        result.setId(problem.getId());
        result.setCreatedAt(problem.getCreatedAt());
        result.setLastModifiedAt(problem.getLastModifiedAt());
        result.setPeople(problem.getPeople().stream().map(Person::getFullName).collect(Collectors.toList()));
        result.setRelations(problem.getPeopleRelations().stream().map(r -> new PeopleKnowRelationDTO(r.getPerson1().getFullName(), r.getPerson2().getFullName())).collect(Collectors.toList()));
        result.setCelebrities(problem.getCelebrities().stream().map(Person::getFullName).collect(Collectors.toList()));
        CelebrityApplication.LOGGER.info("[ProblemRestDTOBuilderImpl.createProblemSolutionResponseDTO] - method end return value =" + result);
        return result;
    }


    @Override
    public ProblemSolutionResponseDTO buildResponseDTO(Problem problem) {
        CelebrityApplication.LOGGER.info("[ProblemRestDTOBuilderImpl.buildResponseDTO] - method begin");
        return problem == null ? null : createProblemSolutionResponseDTO(problem);
    }

    @Override
    public List<ProblemSolutionResponseDTO> buildListResponseDTO(Iterable<Problem> problems) {
        CelebrityApplication.LOGGER.info("[ProblemRestDTOBuilderImpl.buildListResponseDTO] - method begin");
        return problems == null ? new ArrayList<>() :
                StreamSupport.stream(problems.spliterator(), false)
                        .map(this::buildResponseDTO)
                        .collect(Collectors.toList());
    }


}
