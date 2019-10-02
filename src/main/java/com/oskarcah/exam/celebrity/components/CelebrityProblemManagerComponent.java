package com.oskarcah.exam.celebrity.components;

import com.oskarcah.exam.celebrity.dto.ProblemSetRequestDTO;
import com.oskarcah.exam.celebrity.dto.ProblemSolutionResponseDTO;

import java.util.List;

/**
 *
 */
public interface CelebrityProblemManagerComponent {

    /**
     * @return
     */
    List<ProblemSolutionResponseDTO> getAllProblems() throws Exception;

    /**
     * @param id
     * @return
     */
    ProblemSolutionResponseDTO getProblemById(Long id) throws Exception;

    /**
     * @param problemDTO
     * @return
     */
    ProblemSolutionResponseDTO createProblem(ProblemSetRequestDTO problemDTO) throws Exception;

    /**
     * @param id
     * @param problemDTO
     * @return
     */
    ProblemSolutionResponseDTO updateProblemById(Long id, ProblemSetRequestDTO problemDTO) throws Exception;

    /**
     * @param id
     * @return
     */
    void deleteProblemById(Long id) throws Exception;

    /**
     * @param id
     * @return
     */
    boolean existsProblem(Long id) throws Exception;

}
