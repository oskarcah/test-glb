package com.oskarcah.exam.celebrity.components.impl;

import com.oskarcah.exam.celebrity.components.*;
import com.oskarcah.exam.celebrity.dto.ProblemSetRequestDTO;
import com.oskarcah.exam.celebrity.dto.ProblemSolutionResponseDTO;
import com.oskarcah.exam.celebrity.model.KnownPerson;
import com.oskarcah.exam.celebrity.model.Person;
import com.oskarcah.exam.celebrity.model.Problem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class CelebrityProblemManagerComponentImpl implements CelebrityProblemManagerComponent {

    private DataAccessComponent dataAccess;

    private ProblemRestDTOBuilder problemDtoBuilder;

    private ProblemModelBuilder problemModelBuilder;

    private ProblemSolverComponent problemSolver;

    @Autowired
    public CelebrityProblemManagerComponentImpl(DataAccessComponent dataAccess, ProblemRestDTOBuilder problemDtoBuilder, ProblemModelBuilder problemModelBuilder, ProblemSolverComponent problemSolver) {
        this.dataAccess = dataAccess;
        this.problemDtoBuilder = problemDtoBuilder;
        this.problemModelBuilder = problemModelBuilder;
        this.problemSolver = problemSolver;
    }

    @Override
    public List<ProblemSolutionResponseDTO> getAllProblems() {
        Iterable<Problem> problems = dataAccess.findAllProblems();
        return problemDtoBuilder.buildListResponseDTO(problems);
    }

    @Override
    public ProblemSolutionResponseDTO getProblemById(Long id) {
        Optional<Problem> problem = dataAccess.findProblemById(id);
        return problemDtoBuilder.buildResponseDTO(problem.get());
    }

    @Override
    public ProblemSolutionResponseDTO createProblem(ProblemSetRequestDTO problemDTO) {
        Problem problem = problemModelBuilder.buildProblemSet(problemDTO);
        List<Person>  solution = problemSolver.solveProblem(problem);
        problem.setCelebrities(solution);
        if (problem != null) {
            dataAccess.saveProblem(problem);
        }
        return problemDtoBuilder.buildResponseDTO(problem);
    }

    @Override
    public ProblemSolutionResponseDTO updateProblemById(Long id, ProblemSetRequestDTO problemDTO) {
        Optional<Problem> optProblem = dataAccess.findProblemById(id);
        Problem problem = null;
        if (optProblem.isPresent()) {
            problem = optProblem.get();

            // wipe up People and relation from current problem instance
            List<Long> p1 = problem.getPeople().stream().map(Person::getId).collect(Collectors.toList());
            List<Long> p2 = problem.getCelebrities().stream().map(Person::getId).collect(Collectors.toList());
            List<Long> kp = problem.getPeopleRelations().stream().map(KnownPerson::getId).collect(Collectors.toList());
            p1.addAll(p2);
            dataAccess.deletePersonRelationsByIds(kp);
            dataAccess.deletePeopleByIds(p1);
            problem.getPeople().clear();
            problem.getCelebrities().clear();
            problem.getPeopleRelations().clear();

            // update problem instance with new DTO data
            problem = problemModelBuilder.buildProblemSet(problem, problemDTO);
            List<Person>  solution = problemSolver.solveProblem(problem);
            problem.setCelebrities(solution);

            dataAccess.saveProblem(problem);
        }
        return problemDtoBuilder.buildResponseDTO(problem);
    }

    @Override
    public void deleteProblemById(Long id) {
        dataAccess.deleteProblemById(id);
    }

    @Override
    public boolean existsProblem(Long id) {
        return dataAccess.existsProblemById(id);
    }
}
