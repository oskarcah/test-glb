package com.oskarcah.exam.celebrity.exceptions;

import net.bytebuddy.implementation.bytecode.Throw;

import javax.persistence.EntityManager;
import javax.persistence.Query;

public class DataAccessException extends CelebrityException {
    public DataAccessException(Throwable e) {
        super("An error ocurred when accessing DB: " + e);

    }
}
