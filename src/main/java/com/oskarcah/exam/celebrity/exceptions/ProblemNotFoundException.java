package com.oskarcah.exam.celebrity.exceptions;

public class ProblemNotFoundException extends CelebrityException {
    public ProblemNotFoundException() {
        super("There is not problem found in DB");
    }

    public ProblemNotFoundException(Long id) {
        super("A Problem record with id=" + id + " not found in DB");
    }
}
