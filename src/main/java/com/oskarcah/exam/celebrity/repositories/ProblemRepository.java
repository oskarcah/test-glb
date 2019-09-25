package com.oskarcah.exam.celebrity.repositories;

import com.oskarcah.exam.celebrity.model.Problem;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProblemRepository extends CrudRepository<Problem, Long> {


}
