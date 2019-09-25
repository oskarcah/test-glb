package com.oskarcah.exam.celebrity.api;

import com.oskarcah.exam.celebrity.components.DataAccessComponent;
import com.oskarcah.exam.celebrity.components.ProblemInDatabaseBuilder;
import com.oskarcah.exam.celebrity.components.ProblemRestDTOBuilder;
import com.oskarcah.exam.celebrity.model.Problem;
import com.oskarcah.exam.celebrity.dto.ProblemSetRequestDTO;
import com.oskarcah.exam.celebrity.dto.ProblemSolutionResponseDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 *  Base controler for Restul API
 */
@RestController
@Transactional
public class CelebrityApiController {

    private static final Logger LOGGER = LoggerFactory.getLogger(CelebrityApiController.class);

    @Autowired
    private ProblemRestDTOBuilder dtoBuilder;

    @Autowired
    private ProblemInDatabaseBuilder problemBuilder;

    @Autowired
    private DataAccessComponent dataAccess;

    /**
     *   <li>Endpoint http://base_url/celebrities</li>
     *   <li>Verb GET</li>
     *   <li>Description: Get all problemsets</li>
     * @return
     *   Response with 200 code and  a list of all problemsets with theirs solutions stored in database
     */
    @RequestMapping(value="/celebrities", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<List<ProblemSolutionResponseDTO>> getAll() {
        List<ProblemSolutionResponseDTO> response = dtoBuilder.buildListResponseDTO(dataAccess.findAllProblems());
        if (response == null || response.isEmpty()) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
    }

    /**
     *   <li>Endpoint http://base_url/celebrities/{id}</li>
     *   <li>Verb GET</li>
     *   <li>Description: Get problemsets with id= {id}</li>
     *
     * @param  problemId Id of the problem to be sought in database
     *
     * @return
     *   Response with 200 code and  a the problem with its solution or Response 404 if problem with given id is not found.
     */
    @RequestMapping(value="/celebrities/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<ProblemSolutionResponseDTO> getById(@PathVariable("id") Long problemId) {
        Problem problem =  dataAccess.findProblemById(problemId);
        if (problem == null) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        } else {
            ProblemSolutionResponseDTO response = dtoBuilder.buildResponseDTO(problem);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
    }

    /**
     *   <li>Endpoint http://base_url/celebrities/{id}</li>
     *   <li>Verb DELETE</li>
     *   <li>Description: Delete problemset with id= {id}</li>
     *
     * @param  problemId Id of the problem to be sought in database
     *
     * @return
     *   Response with 204 codeif problem instance is deleted or Response 404 if problem with given id is not found.
     */
    @RequestMapping(value="/celebrities/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<String> delete(@PathVariable("id") Long problemId) {
        if (!dataAccess.existsProblemById(problemId)) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        } else {
            dataAccess.deleteProblemById(problemId);
            return new ResponseEntity<>("deleted", HttpStatus.NO_CONTENT);
        }
    }

    /**
     *   <li>Endpoint http://base_url/celebrities/{id}</li>
     *   <li>Verb PUT</li>
     *   <li>Description: Update problemset with id= {id} and perform the solution algorithm again.</li>
     *
     * @param  problemId Id of the problem to be sought in database. Data is in request body
     *
     * @return
     *   Response with 200 code if problem instance and its solution is updated in database or Response 404 if problem with given id is not found.
     */
    @RequestMapping(value="/celebrities/{id}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_UTF8_VALUE, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<ProblemSolutionResponseDTO> updateById(@PathVariable("id") Long problemId, @RequestBody ProblemSetRequestDTO request) {
        Problem problem = dataAccess.findProblemById(problemId);
        if (problem == null) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        } else {
            problem = problemBuilder.updateProblemSet(problem , request);
            return new ResponseEntity<>(dtoBuilder.buildResponseDTO(problem), HttpStatus.OK);
        }
    }

    /**
     *   <li>Endpoint http://base_url/celebrities</li>
     *   <li>Verb POST</li>
     *   <li>Description: create a new problemset instance, perform the solution algorithm and stores the problemset in database.</li>
     *
     * @param  request instance of {@link ProblemSetRequestDTO} with the problem information. Data is in request body
     *
     * @return
     *   Response with 201 code if problem instance and its solution is updated in database or Response 404 if problem with given id is not found.
     */
    @RequestMapping(value="/celebrities", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> post(@RequestBody ProblemSetRequestDTO request) {
        Problem problem = problemBuilder.buildProblemSet(request);
        return new ResponseEntity<>("" + problem.getId(), HttpStatus.CREATED);
    }



}