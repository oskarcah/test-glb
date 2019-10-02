package com.oskarcah.exam.celebrity.components;

import com.oskarcah.exam.celebrity.exceptions.CelebrityException;
import com.oskarcah.exam.celebrity.model.Person;
import com.oskarcah.exam.celebrity.model.Problem;

import java.util.List;

/**
 *
 */
public interface ProblemSolverComponent {
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
    int[][] createRelationshipMatrix(Problem problem);

    /**
     *  Performs problem solution by
     *
     *  1) Creation of relationship matrix, this is an n x n Matrix where A[i][j] == 1 implies that person i knows person j
     *  2) Use the matrix to find if a k-th person is celebrity. That is if row k is all 1 (everybody know him/her) and column k is all 0 (he/she doesn't know anybody)
     * @param problem
     * @return List of person with information of person that fulfill criteria to be a celebrity
     */
    List<Person> solveProblem(Problem problem);
}
