package com.oskarcah.exam.celebrity.components;

import com.oskarcah.exam.celebrity.model.Problem;
import com.oskarcah.exam.celebrity.model.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Component with business logic layer, namely the algorithm for the solution of the celebrity problem.
 */
@Component
public class ProblemSolver {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProblemSolver.class);

    public ProblemSolver() {
        super();
    }

    /**
     * Creates a n x n matrix representing the "knows" relationship between 2 people.
     *
     * This matrix has the following features:
     *
     * <li>n x n matrix if problem has n people</li>
     * <li>M[i][j] == 1 if person i knows person j, 0 otherwise</li>
     * <li>M[i][j] == 1 doesn't implies that M[j][i]. That is, the  "knows" relation is not symmetric, so, the matrix is not necessarily a symmetric matrix</li>
     * <li> The diagonal values M[i][i] are not relevant for problem solution, because it's trivial that a person knows his/herself</li>
     *
     * @param problem
     * @return
     */
    public int[][] createRelationshipMatrix(final Problem problem) {
        final int n = problem.getPeopleCount();
        int[][] matrix = new int[n][n];

        //iterate over all people and fill the people relationship matrix in
        problem.getPeopleRelations().stream().forEach(relation -> {
            if (relation.getPerson1() != null && relation.getPerson2() != null) {
                matrix[relation.getPerson1().getIndex()][relation.getPerson2().getIndex()] = 1;
            }
        });
        return matrix;
    }

    /**
     *  Performs problem solution by
     *
     *  1) Creation of relationship matrix, this is an n x n Matrix where A[i][j] == 1 implies that person i knows person j
     *  2) Use the matrix to find if a k-th person is celebrity. That is if row k is all 1 (everybody know him/her) and column k is all 0 (he/she doesn't know anybody)
     * @param problem
     * @return List of person with information of person that fulfill criteria to be a celebrity
     */
    public List<Person> solveProblem(final Problem problem) {
        int[][] relationships = createRelationshipMatrix(problem);
        int n = problem.getPeopleCount();

        // array with counters of known by people. Example, if knownBy[i] == 5 implies that person is is known by 5 people
        int[] knows = new int[n];
        int[] knownBy = new int[n];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                // diagonal is not taken into account because is trivial a person knows him/herself
                if (i != j && relationships[i][j] == 1) {
                    knows[i]++;
                    knownBy[j]++;
                }
            }
        }

        // celebrities are people that count of knows = 0 and count of knowBy's = n - 1
        return problem.getPeople().stream()
                .filter(p -> {
                    return knows[p.getIndex()] == 0 && knownBy[p.getIndex()] == n - 1;
                })
                .collect(Collectors.toList());

    }
}
