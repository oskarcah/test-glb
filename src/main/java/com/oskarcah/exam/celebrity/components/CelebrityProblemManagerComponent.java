package com.oskarcah.exam.celebrity.components;

import com.oskarcah.exam.celebrity.dto.ProblemSetRequestDTO;
import com.oskarcah.exam.celebrity.dto.ProblemSolutionResponseDTO;
import com.oskarcah.exam.celebrity.model.Problem;

import java.util.List;

public interface CelebrityProblemManagerComponent {

    /**
     *
     * @return
     */
    List<ProblemSolutionResponseDTO> getAllProblems();

    /**
     *
     * @param id
     * @return
     */
    ProblemSolutionResponseDTO getProblemById(Long id);

    /**
     *
     * @param problemDTO
     * @return
     */
    ProblemSolutionResponseDTO createProblem(ProblemSetRequestDTO problemDTO);

    /**
     *
     * @param id
     * @param problemDTO
     * @return
     */
    ProblemSolutionResponseDTO updateProblemById(Long id, ProblemSetRequestDTO problemDTO);

    /**
     *
     * @param id
     * @return
     */
    void deleteProblemById(Long id);

    /**
     *
     * @param id
     * @return
     */
    boolean existsProblem(Long id);

}
