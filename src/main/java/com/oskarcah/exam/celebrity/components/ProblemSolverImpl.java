package com.oskarcah.exam.celebrity.components;

import com.oskarcah.exam.celebrity.application.CelebrityApplication;
import com.oskarcah.exam.celebrity.model.Problem;
import com.oskarcah.exam.celebrity.model.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Component with business logic layer, namely the algorithm for the solution of the celebrity problem.
 */
@Component
public class ProblemSolverImpl implements ProblemSolver {

    public ProblemSolverImpl() {
        super();
    }

    @Override
    public int[][] createRelationshipMatrix(final Problem problem) {
        CelebrityApplication.LOGGER.info("[ProblemSolverImpl.createRelationshipMatrix] - method begin  problem=" + problem);
        final int n = problem.getPeopleCount();
        int[][] matrix = new int[n][n];

        //iterate over all people and fill the people relationship matrix in
        problem.getPeopleRelations().stream().forEach(relation -> {
            if (relation.getPerson1() != null && relation.getPerson2() != null) {
                matrix[relation.getPerson1().getIndex()][relation.getPerson2().getIndex()] = 1;
            }
        });
        CelebrityApplication.LOGGER.info("[ProblemSolverImpl.createRelationshipMatrix] - method end  returned matrix of size "+ (matrix == null ? 0 : matrix.length));
        return matrix;
    }

    @Override
    public List<Person> solveProblem(final Problem problem) {
        CelebrityApplication.LOGGER.info("[ProblemSolverImpl.solveProblem] - method begin  problem=" + problem);
        int[][] relationships = createRelationshipMatrix(problem);
        int n = problem.getPeopleCount();

        // array with counters of known by people. Example, if knownBy[i] == 5 implies that person is is known by 5 people
        int[] knows = new int[n];
        int[] knownBy = new int[n];

        CelebrityApplication.LOGGER.info("[ProblemSolverImpl.solveProblem] - beginning loop for calculate knows and knownby array");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                // diagonal is not taken into account because is trivial a person knows him/herself
                if (i != j && relationships[i][j] == 1) {
                    knows[i]++;
                    knownBy[j]++;
                }
            }
        }

        CelebrityApplication.LOGGER.info("[ProblemSolverImpl.solveProblem] - knows =" + Arrays.toString(knows));
        CelebrityApplication.LOGGER.info("[ProblemSolverImpl.solveProblem] - knownBy =" + Arrays.toString(knownBy));

        CelebrityApplication.LOGGER.info("[ProblemSolverImpl.solveProblem] - method end");
        // celebrities are people that count of knows = 0 and count of knowBy's = n - 1
        return problem.getPeople().stream()
                .filter(p -> {
                    return knows[p.getIndex()] == 0 && knownBy[p.getIndex()] == n - 1;
                })
                .collect(Collectors.toList());
    }
}
