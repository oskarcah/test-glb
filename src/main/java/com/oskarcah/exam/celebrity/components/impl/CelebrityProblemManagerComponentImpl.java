package com.oskarcah.exam.celebrity.components.impl;

import com.oskarcah.exam.celebrity.components.*;
import com.oskarcah.exam.celebrity.dto.ProblemSetRequestDTO;
import com.oskarcah.exam.celebrity.dto.ProblemSolutionResponseDTO;
import com.oskarcah.exam.celebrity.exceptions.*;
import com.oskarcah.exam.celebrity.model.KnownPerson;
import com.oskarcah.exam.celebrity.model.Person;
import com.oskarcah.exam.celebrity.model.Problem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class CelebrityProblemManagerComponentImpl implements CelebrityProblemManagerComponent {

    private DataAccessComponent dataAccess;

    private ProblemRestDTOBuilder problemDtoBuilder;

    private ProblemModelBuilder problemModelBuilder;

    private ProblemSolverComponent problemSolver;

    private ProblemValidatorComponent problemValidator;

    @Autowired
    public CelebrityProblemManagerComponentImpl(DataAccessComponent dataAccess, ProblemRestDTOBuilder problemDtoBuilder, ProblemModelBuilder problemModelBuilder, ProblemSolverComponent problemSolver, ProblemValidatorComponent problemValidator) {
        this.dataAccess = dataAccess;
        this.problemDtoBuilder = problemDtoBuilder;
        this.problemModelBuilder = problemModelBuilder;
        this.problemSolver = problemSolver;
        this.problemValidator = problemValidator;
    }

    /**
     *
     * @param problemDTO
     * @throws CelebrityException
     */
    private void validateProblemDTO(ProblemSetRequestDTO problemDTO) throws CelebrityException {
        if (!problemValidator.validProblemSize(problemDTO)) {
            throw new TooFewPeopleException();
        }
        if (!problemValidator.validateNotEmptyNames(problemDTO)) {
            throw new EmptyNameException();
        }
        if (!problemValidator.validateNotEmptyRelations(problemDTO)) {
            throw new EmptyRelationsException();
        }
        List<String> repeatedNames = problemValidator.repeatedPeopleNames(problemDTO);
        if (!repeatedNames.isEmpty()) {
            throw new RepeatedPersonException(Arrays.toString(repeatedNames.toArray()));
        }
        List<String> notExistingPeopleInRelations = problemValidator.notExistingPeopleInRelations(problemDTO);
        if (!notExistingPeopleInRelations.isEmpty()) {
            throw new NotExistingPersonInRelationException(Arrays.toString(notExistingPeopleInRelations.toArray()));
        }
    }

    @Override
    public List<ProblemSolutionResponseDTO> getAllProblems() throws Exception {
        Iterable<Problem> problems = dataAccess.findAllProblems();
        return problemDtoBuilder.buildListResponseDTO(problems);
    }

    @Override
    public ProblemSolutionResponseDTO getProblemById(Long id) throws Exception {
        return problemDtoBuilder.buildResponseDTO(dataAccess.findProblemById(id).orElseThrow(() -> new ProblemNotFoundException(id)));
    }

    @Override
    public ProblemSolutionResponseDTO createProblem(ProblemSetRequestDTO problemDTO) throws Exception {
        validateProblemDTO(problemDTO);
        Problem problem = problemModelBuilder.buildProblemSet(problemDTO);
        List<Person> solution = problemSolver.solveProblem(problem);
        problem.setCelebrities(solution);
        if (problem != null) {
            dataAccess.saveProblem(problem);
        }
        return problemDtoBuilder.buildResponseDTO(problem);
    }


    @Override
    public ProblemSolutionResponseDTO updateProblemById(Long id, ProblemSetRequestDTO problemDTO) throws Exception {
        Problem problem = dataAccess.findProblemById(id).orElseThrow(() -> new ProblemNotFoundException(id));
        validateProblemDTO(problemDTO);

        // Problem data is valid, so we can go on.
        // wipe up People and relation from current problem instance
        List<Long> p1 = problem.getPeople().stream().map(Person::getId).collect(Collectors.toList());
        List<Long> p2 = problem.getCelebrities().stream().map(Person::getId).collect(Collectors.toList());
        List<Long> kp = problem.getPeopleRelations().stream().map(KnownPerson::getId).collect(Collectors.toList());
        p1.addAll(p2);

        problem.getPeople().clear();
        problem.getCelebrities().clear();
        problem.getPeopleRelations().clear();
        problem.getPeople().stream().forEach(p -> {p.setProblem(null);});

        dataAccess.deletePersonRelationsByIds(kp);
        dataAccess.deletePeopleByIds(p1);

        // update problem instance with new DTO data
        problem = problemModelBuilder.buildProblemSet(problem, problemDTO);
        List<Person> solution = problemSolver.solveProblem(problem);
        problem.setCelebrities(solution);
        dataAccess.saveProblem(problem);
        return problemDtoBuilder.buildResponseDTO(problem);
    }

    @Override
    public void deleteProblemById(Long id) throws Exception {
        if (!existsProblem(id)) {
            throw new ProblemNotFoundException(id);
        }
        dataAccess.deleteProblemById(id);
    }

    @Override
    public boolean existsProblem(Long id) throws Exception {
        return dataAccess.existsProblemById(id);
    }
}
