package com.oskarcah.exam.celebrity.exceptions;

public class EmptyRelationsException extends CelebrityException {
    public EmptyRelationsException() {
        super("There are one or more relations with empty or null names");
    }
}
