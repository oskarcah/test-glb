package com.oskarcah.exam.celebrity.components;

import com.oskarcah.exam.celebrity.dto.ProblemSetRequestDTO;

import java.util.List;

/**
 *
 */
public interface ProblemValidatorComponent {

    /**
     * @param problem
     * @return
     */
    boolean validProblemSize(final ProblemSetRequestDTO problem);

    /**
     * @param problem
     * @return
     */
    List<String> repeatedPeopleNames(final ProblemSetRequestDTO problem);

    /**
     * @param problem
     * @return
     */
    boolean validateNotEmptyNames(final ProblemSetRequestDTO problem);

    /**
     * @param problem
     * @return
     */
    boolean validateNotEmptyRelations(final ProblemSetRequestDTO problem);

    /**
     * @param problem
     * @return
     */
    List<String> notExistingPeopleInRelations(final ProblemSetRequestDTO problem);

}
