package com.oskarcah.exam.celebrity.api;

import com.oskarcah.exam.celebrity.application.CelebrityApplication;
import com.oskarcah.exam.celebrity.components.CelebrityProblemManagerComponent;
import com.oskarcah.exam.celebrity.dto.ProblemSetRequestDTO;
import com.oskarcah.exam.celebrity.dto.ProblemSolutionResponseDTO;
import com.oskarcah.exam.celebrity.exceptions.ProblemNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;

/**
 * Base controler for Restul API
 */
@RestController
@Transactional
public class CelebrityApiController {


    private CelebrityProblemManagerComponent problemManager;

    @Autowired
    public CelebrityApiController(CelebrityProblemManagerComponent problemManager) {
        this.problemManager = problemManager;
    }

    /**
     * Method for handling exceptions in API operations
     *
     * @param t Throwable instance
     * @return Response entity with status = 500 and a descriptive error message
     */
    @ExceptionHandler({Throwable.class})
    public ResponseEntity<String> ExceptionHandler(Throwable t) {
        CelebrityApplication.LOGGER.info("[CelebrityApiController.ExceptionHandler] - exceptionhandler begin Exception=" + t);

        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        t.printStackTrace(pw);
        CelebrityApplication.LOGGER.info("[CelebrityApiController.ExceptionHandler] - trace: " + sw.toString());

        String msg = "An Exception ocurred while performing operation: " + t.getMessage();
        return new ResponseEntity<>(msg, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * <li>Endpoint http://base_url/celebrities</li>
     * <li>Verb GET</li>
     * <li>Description: Get all problemsets</li>
     *
     * @return Response with 200 code and  a list of all problemsets with theirs solutions stored in database
     */
    @RequestMapping(value = "/celebrities", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<List<ProblemSolutionResponseDTO>> getAll() throws Exception {
        CelebrityApplication.LOGGER.info("[CelebrityApiController.getAll] - method for endpoint \"/celebrities\" verb GET begin");
        List<ProblemSolutionResponseDTO> response = null;
        try {
            response = problemManager.getAllProblems();
            CelebrityApplication.LOGGER.info("[CelebrityApiController.getAll] - method for endpoint \"/celebrities\"  verb GET  with 200 status");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (ProblemNotFoundException e) {
            CelebrityApplication.LOGGER.info("[CelebrityApiController.getAll] - method for endpoint \"/celebrities\"  verb GET end  with 404 status");
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    /**
     * <li>Endpoint http://base_url/celebrities/{id}</li>
     * <li>Verb GET</li>
     * <li>Description: Get problemsets with id= {id}</li>
     *
     * @param problemId Id of the problem to be sought in database
     * @return Response with 200 code and  a the problem with its solution or Response 404 if problem with given id is not found.
     */
    @RequestMapping(value = "/celebrities/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<ProblemSolutionResponseDTO> getById(@PathVariable("id") Long problemId) throws Exception {
        CelebrityApplication.LOGGER.info("[CelebrityApiController.getById] - method for endpoint \"/celebrities/{id}\" verb GET begin");
        ProblemSolutionResponseDTO response = null;
        try {
            response = problemManager.getProblemById(problemId);
            CelebrityApplication.LOGGER.info("[CelebrityApiController.getById] - a problem instance with id=" + problemId + " was found");
            CelebrityApplication.LOGGER.info("[CelebrityApiController.getById] - method for endpoint \"/celebrities/{id}\" verb GET end  with 200 status");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (ProblemNotFoundException e) {
            CelebrityApplication.LOGGER.info("[CelebrityApiController.getById] - not found any problem instance with id=" + problemId);
            CelebrityApplication.LOGGER.info("[CelebrityApiController.getById] - method for endpoint \"/celebrities/{id}\" verb GET  end  with 404 status");
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    /**
     * <li>Endpoint http://base_url/celebrities/{id}</li>
     * <li>Verb DELETE</li>
     * <li>Description: Delete problemset with id= {id}</li>
     *
     * @param problemId Id of the problem to be sought in database
     * @return Response with 204 codeif problem instance is deleted or Response 404 if problem with given id is not found.
     */
    @RequestMapping(value = "/celebrities/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<String> delete(@PathVariable("id") Long problemId) throws Exception {
        CelebrityApplication.LOGGER.info("[CelebrityApiController.delete] - method for endpoint \"/celebrities/{id}\"  verb DELETE begin");
        try {
            problemManager.deleteProblemById(problemId);
            CelebrityApplication.LOGGER.info("[CelebrityApiController.delete] - method for endpoint \"/celebrities/{id}\" verb DELETE  end  with 204 status");
            return new ResponseEntity<>("deleted id=" + problemId, HttpStatus.NO_CONTENT);
        } catch (ProblemNotFoundException e) {
            CelebrityApplication.LOGGER.info("[CelebrityApiController.delete] - not found any problem instance with id=" + problemId);
            CelebrityApplication.LOGGER.info("[CelebrityApiController.delete] - method for endpoint \"/celebrities/{id}\" verb DELETE  end  with 404 status");
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    /**
     * <li>Endpoint http://base_url/celebrities/{id}</li>
     * <li>Verb PUT</li>
     * <li>Description: Update problemset with id= {id} and perform the solution algorithm again.</li>
     *
     * @param problemId Id of the problem to be sought in database. Data is in request body
     * @return Response with 200 code if problem instance and its solution is updated in database or Response 404 if problem with given id is not found.
     */
    @RequestMapping(value = "/celebrities/{id}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_UTF8_VALUE, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<ProblemSolutionResponseDTO> updateById(@PathVariable("id") Long problemId, @RequestBody ProblemSetRequestDTO request) throws Exception {
        CelebrityApplication.LOGGER.info("[CelebrityApiController.updateById] - method for endpoint \"/celebrities/{id}\"  verb PUT begin request=" + request);
        try {
            CelebrityApplication.LOGGER.info("[CelebrityApiController.updateById] - a problem instance with id=" + problemId + " was found");
            ProblemSolutionResponseDTO response = problemManager.updateProblemById(problemId, request);
            CelebrityApplication.LOGGER.info("[CelebrityApiController.updateById] - method for endpoint \"/celebrities/{id}\" verb PUT  end  with 200 status");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (ProblemNotFoundException e) {
            CelebrityApplication.LOGGER.info("[CelebrityApiController.updateById] - not found any problem instance with id=" + problemId);
            CelebrityApplication.LOGGER.info("[CelebrityApiController.updateById] - method for endpoint \"/celebrities/{id}\" verb PUT  end  with 404 status");
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    /**
     * <li>Endpoint http://base_url/celebrities</li>
     * <li>Verb POST</li>
     * <li>Description: create a new problemset instance, perform the solution algorithm and stores the problemset in database.</li>
     *
     * @param request instance of {@link ProblemSetRequestDTO} with the problem information. Data is in request body
     * @return Response with 201 code if problem instance and its solution is updated in database or Response 404 if problem with given id is not found.
     */
    @RequestMapping(value = "/celebrities", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> post(@RequestBody ProblemSetRequestDTO request) throws Exception {
        CelebrityApplication.LOGGER.info("[CelebrityApiController.post] - method for endpoint \"/celebrities/{id}\"  verb POST begin request=" + request);
        ProblemSolutionResponseDTO problemDTO = problemManager.createProblem(request);
        CelebrityApplication.LOGGER.info("[CelebrityApiController.post] - a problem instance with id=" + problemDTO.getId() + " was created");
        CelebrityApplication.LOGGER.info("[CelebrityApiController.updateById] - method for endpoint \"/celebrities/{id}\" verb POST  end  with 201 status");
        return new ResponseEntity<>("" + problemDTO.getId(), HttpStatus.CREATED);
    }


}