package com.oskarcah.exam.celebrity.exceptions;

public class NotExistingPersonInRelationException extends CelebrityException {
    public NotExistingPersonInRelationException(String name) {
        super("The names " + name + " are in some relations but are not in people list");
    }
}
